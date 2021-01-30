package com.travel.fj.controller;

import com.travel.fj.domain.Guide;
import com.travel.fj.queryhelper.GuideQueryHelper;
import com.travel.fj.service.GuideService;
import com.travel.fj.service.GuideTypeService;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("guide")
public class GuideController extends BaseController {

    @Autowired
    GuideService guideService;

    @Autowired
    GuideTypeService guideTypeService;

    @RequestMapping("guideDetail/{guideId}")
    public String guideDetail(Model model, @PathVariable String guideId){

        try{
            Integer.parseInt(guideId);
        }catch(NumberFormatException e){
            return "error_page/work_access_error_page";
        }

        if(!isValidGuide(Integer.parseInt(guideId))){

            return "error_page/work_access_error_page";
        }
        Guide detail=guideService.getGuideById(Integer.parseInt(guideId));
        if(detail.getGuideStatus()==0){

            return "error_page/work_access_error_page";
        }
        model.addAttribute("otherLinkGuide",guideService.getGuideByAttracIdU(detail.getGuideAttrac().getAttracId()));
        model.addAttribute("guideDetail",detail);
        guideViewUpDate(detail);
        return "guide_page/guide_detail_page";

    }

    //获取随机游记，实现首页随机游记 部分刷新
    @RequestMapping(value="randomGuideAjax",produces = "application/json")
    @ResponseBody
    public List<Guide> randomGuideAjax(){
        //使用Service层的方法进行查找
        List<Guide> r=guideService.getRandomGuideU();

        return r;
    }

    @RequestMapping("showGuide/{guideTypeId}")
    public String showGuideByGuideTypeId(@PathVariable String guideTypeId, Model model){
        Integer typeId;
        try {
            //防止输入非数字
            typeId=Integer.parseInt(guideTypeId);
        }catch(NumberFormatException e){
            //返回错误页面
            return "error_page/guide_list_access_error_page";
        }
        //输入错误的ID，即无法找到相应类型时
        if(guideTypeService.getGuideTypeById(typeId)==null){
            //返回错误页面
            return "error_page/guide_list_access_error_page";
        }

        //查找到类型信息后将ID保存到model，在页面利用Ajax向搜索用方法searchAttrac()查找
        model.addAttribute("queryGuideType", typeId);
        return "guide_page/guide_show_guide_by_guide_type_id";
    }

    @GetMapping("showGuide")
    public String showGuideByKeyword(String keyword,Model model){
        model.addAttribute("queryGuideTitle", keyword);
        return "guide_page/guide_show_guide_by_keyword";
    }

    @PostMapping(value = "searchGuide",produces="application/json")
    @ResponseBody                       //spring自动将post提交的数据进行封装
    public Page searchWork(GuideQueryHelper qData){

        Page p=new Page();
        p.setPageNo(qData.getCurrentPageNum());
        //使用Service层的组合查询方法进行查找
        Page result=guideService.loadQueryAndPagedGuides(qData,p);

        //返回结果
        return result;
    }

    @RequestMapping(value = "getGuidePic/{guideId}", produces="application/json")
    @ResponseBody
    public ResultInfo getGuidePic(@PathVariable String guideId) throws Exception{

        String fileList=guideService.getGuidePicById(Integer.parseInt(guideId));
        if(fileList!=null) {
            String[] fList = fileList.split("#");
            String encodePic= PicFileUtil.encodePicToBase64(fList);
            return new ResultInfo(true, "获取成功", encodePic);
        }
        return new ResultInfo(false, "没有图片", null);
    }

    @RequestMapping(value="getGuideSinglePic/{guideId}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getGuideSinglePic(@PathVariable String guideId){
        String picList=guideService.getGuidePicById(Integer.parseInt(guideId));

        if(picList!=null){
            String[] l=picList.split("#");
            return  PicFileUtil.getFirstPic(l);
        }
        else
            return null;
    }


    private void guideViewUpDate(Guide g){
        g.setGuideClickCount(g.getGuideClickCount()+1);
        guideService.updateGuideClickCount(g);
    }
    private boolean isValidGuide(Integer guideId){
        Guide preCheck=guideService.getGuideById(guideId);
        if(preCheck!=null){
            return true;
        }
        else
            return false;
    }
}
