package com.travel.fj.controller;

import com.travel.fj.domain.Attraction;
import com.travel.fj.domain.City;
import com.travel.fj.queryhelper.AttracQueryHelper;
import com.travel.fj.service.*;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*checked*/
@Controller
@RequestMapping("/attrac")
public class AttractionController {

    private static final Logger logger= LogManager.getLogger(AttractionController.class);

    @Autowired
    CityService cityService;

    @Autowired
    AttractionService attractionService;

    @Autowired
    AttractionTypeService attractionTypeService;

    @Autowired
    WorkService workService;

    @Autowired
    GuideService guideService;

    //根据城市查找景点
    @RequestMapping("showAttracs/{cityName}")
    public String showAttracByCityName(@PathVariable String cityName, Model model){

        //根据名称查找城市信息
        City c=cityService.getCityByName(cityName);
        if(c!=null) {

            //查找到城市信息后将ID提取，保存到model，在页面利用Ajax向搜索用方法查找
            model.addAttribute("queryCityId", c.getCityId());
            //前往具体界面
         return "attrac_page/attrac_show_attrac_by_cityname";
        }
        //否则返回错误页面
        else {
            return "error_page/attrac_list_access_error_page";
        }

    }

    //根据景点类型查找景点
    @RequestMapping("showAttracsT/{attracTypeId}")
    public String showAttracByAttracTypeId(@PathVariable String attracTypeId, Model model){
        Integer typeId;
        try {
            //防止输入非数字
              typeId=Integer.parseInt(attracTypeId);
        }catch(NumberFormatException e){
            //返回错误页面
            return "error_page/attrac_list_access_error_page";
        }
        //输入错误的ID，即无法找到相应类型时
        if(attractionTypeService.getAttracTypeById(typeId)==null){
            //返回错误页面
            return "error_page/attrac_list_access_error_page";
        }

        //查找到类型信息后将ID保存到model，在页面利用Ajax向搜索用方法searchAttrac()查找
        model.addAttribute("queryAttracTypeId", typeId);
        return "attrac_page/attrac_show_attrac_by_attrac_type_id";
    }

    //根据关键字查找景点
    @GetMapping("showAttracs")
                                   //Get提交查询关键字
    public String attracSearchQuery(String keyword,Model model){
        //将关键字保存到model，在页面利用Ajax向搜索用方法searchAttrac()查找
        model.addAttribute("queryAttracName",keyword);
        return "attrac_page/attrac_show_attrac_by_key_word";
    }

    //搜索方法，Ajax方法
    @PostMapping(value = "searchAttrac",produces="application/json")
    @ResponseBody                       //spring自动将post提交的数据进行封装
    public Page searchAttrac( AttracQueryHelper qData){

        Page p = new Page();
        p.setPageNo(qData.getCurrentPageNum());
        //使用Service层的组合查询方法进行查找
        Page result=attractionService.loadQueryAndPagedAttractions(qData,p);

        //返回结果
        return result;
    }

    //获取景点排行，Ajax方法
    @RequestMapping(value = "popularAttrac",produces="application/json")
    @ResponseBody
    public List<Attraction> popularAttrac(){

        //使用Service层的方法进行查找
        List<Attraction> r=attractionService.getPopularAttrac();

        return r;
    }

    //获取随机景点，实现首页随机景点部分刷新
    @RequestMapping(value="randomAttracAjax",produces = "application/json")
    @ResponseBody
    public List<Attraction>  randomAttracAjax(){
        //使用Service层的方法进行查找
        List<Attraction> r=attractionService.getRandomAttrac();

        return r;
    }

    //景点详情页
    @RequestMapping("attracDetail/{attracId}")
    public String attracDetail(@PathVariable String attracId,Model model){
        Integer id;
        //防止输入非数字
        try {
            id = Integer.parseInt(attracId);
        }catch (NumberFormatException e){
            return  "error_page/attrac_access_error_page";
        }
        //输入错误的ID，即无法找到相应类型时
        if(attractionService.getAttractionById(id)==null){
            return  "error_page/attrac_access_error_page";
        }

        //获取景点具体信息
        Attraction attrac=attractionService.getAttractionById(id);
        //为搜寻同城景点构造组合查询参数
        AttracQueryHelper aqh=new AttracQueryHelper();
        aqh.setQueryAttracCity(attrac.getAttracCity().getCityId());

        //浏览计数+1
        pageViewUpDate(attrac);

        //向model保存网页显示所需的相关信息
        //景点详情
        model.addAttribute("attracDetail",attrac);
        //关联景点
        model.addAttribute("otherAttrac",attractionService.loadQueryAttractions(aqh));
        //该景点的游记
        model.addAttribute("linkWork",workService.getWorkByAttracIdU(attrac.getAttracId()));
        //该景点的指南
        model.addAttribute("linkGuide",guideService.getGuideByAttracIdU(attrac.getAttracId()));
        //返回结果页
        return "attrac_page/attrac_detail_page";
    }

    //获取景点首图，用于景点的卡片布局时显示用
    @RequestMapping(value="getAttracSinglePic/{attracId}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getAttracSinglePic(@PathVariable String attracId){
        //防止输入非数字
        try{
            Integer.parseInt(attracId);
        }catch (NumberFormatException e){
            logger.error("无效ID,只允许用数字");
            return null;
        }

        //获取景点图片列表
        String picList=attractionService.getAttractionPicFileList(Integer.parseInt(attracId));

        //景点有图片时获取其第一张图片
        if(picList!=null){
            //分割图片记录串
            String[] l=picList.split("#");
            //返回第一张图片
            return  PicFileUtil.getFirstPic(l);
        }
        //否则返回空
        else
            return null;
    }

    /*将景点图片以Base64编码返回*/
    @RequestMapping(value = "getAttracPic/{attracId}", produces="application/json")
    @ResponseBody
    public ResultInfo getAttracPic(@PathVariable String attracId) {

        String fileList=attractionService.getAttractionPicFileList(Integer.parseInt(attracId));
        if(fileList!=null) {
            String[] fList = fileList.split("#");
            String encodePic=PicFileUtil.encodePicToBase64(fList);

            return new ResultInfo(true, "获取成功", encodePic);
        }
        return new ResultInfo(false, "没有图片", null);
    }

    //以字节数组返回城市的介绍图
    @RequestMapping(value="getCityPic/{cityName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getCityPic(@PathVariable String cityName){

        City c=cityService.getCityByName(cityName);
        if(c!=null) {
            Integer id = c.getCityId();
            byte[] pic = cityService.getCityPic(id);
            if (pic != null) {

                return pic;
            }
            //城市无图返回空
            else
                return null;
        }
        else//输入错误返回空
            return  null;

    }


    private void pageViewUpDate(Attraction a){
        a.setAttracClickCount(a.getAttracClickCount()+1);
        attractionService.updateAttracClickCount(a);
    }
}
