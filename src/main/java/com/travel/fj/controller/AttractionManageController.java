package com.travel.fj.controller;



import com.travel.fj.domain.Attraction;
import com.travel.fj.domain.AttractionType;
import com.travel.fj.domain.City;
import com.travel.fj.queryhelper.AttracQueryHelper;
import com.travel.fj.service.AttractionService;
import com.travel.fj.service.AttractionTypeService;
import com.travel.fj.service.CityService;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


@Controller
@RequestMapping("attracMgr")
public class AttractionManageController extends BaseController {




    @Autowired
    AttractionTypeService attractionTypeService;

    @Autowired
    CityService cityService;

    @Autowired
    AttractionService attractionService;



    @GetMapping("createAttrac")
    public String toCreateAttrac(Model model){
        model.addAttribute("newAttrac",new Attraction());
        return "attrac_mgr_page/attrac_create_page";
    }

    @PostMapping("createAttrac")
    public String CreateAttrac(Attraction attraction, MultipartFile[] attracpic,
                               String type,String city)throws Exception{

        //保存类型和城市信息
        saveTypeAndCity(attraction,type,city);
        //保存图片，应该处理未上传图片的情况
        if(!attracpic[0].isEmpty()) {
            PicFileUtil.saveAttracPic(attracpic, attraction);
        }
        else;//空图时无动作，保持空
        //保存景点
        attractionService.createAttraction(attraction);
        return "redirect:/attracMgr/loadAllAttractions";
    }

    @RequestMapping("loadAllAttractions")
    public String loadAll(Model model){
        //model.addAttribute("attractionList",attractionService.loadAllAttractions());
        return "attrac_mgr_page/attrac_manage_page";
    }

    @PostMapping(value = "loadAllAttractionsJ",produces="application/json")
    @ResponseBody
    public Page loadAllJ( AttracQueryHelper qData) throws Exception{

        Page p=new Page();
        System.out.println(qData.getCurrentPageNum());
        p.setPageNo(qData.getCurrentPageNum());
        Page result =attractionService.loadQueryAndPagedAttractions(qData,p);
        return result;

    }

    @PostMapping(value = "loadAllAttractionsAdmin",produces="application/json")
    @ResponseBody
    public Page loadAllAdmin( AttracQueryHelper qData) throws Exception{

        Page p=new Page();
        System.out.println(qData.getCurrentPageNum());
        p.setPageNo(qData.getCurrentPageNum());
        Page result =attractionService.loadQueryAndPagedAttractionsForAdmin(qData,p);
        return result;

    }

    @GetMapping(value = "loadAllAttractionsJ",produces="application/json")
    @ResponseBody
    public Page loadAllJG(Model model, AttracQueryHelper qData,Page p){

        Page result=attractionService.loadQueryAndPagedAttractions(qData,p);
        return result;

    }

    @RequestMapping("attractionDetail/{attracId}")
    public String attracDetail(@PathVariable String attracId,Model model){
        Integer id=Integer.parseInt(attracId);
        model.addAttribute("attracDetail",attractionService.getAttractionById(id));
        return "attrac_mgr_page/attrac_detail_page";
    }

    @GetMapping("updateAttraction/{attracId}")
    public String toUpdate(@PathVariable String attracId,Model model){
        Attraction attraction=attractionService.getAttractionById(Integer.parseInt(attracId));
        model.addAttribute("loadedAttrac",attraction);
        return "attrac_mgr_page/attrac_update_page";
    }

    @PostMapping("updateAttraction")
    public String update(Attraction attraction, MultipartFile[] attracpic,
                         String type,String city){

        if(!attracpic[0].isEmpty()){
            //bug:删除旧图
//            String oldPicStr=attractionService.getAttractionPicFileList(attraction.getAttracId());
//            String[] oldPicList=oldPicStr.split("#");
//            for(String s:oldPicList){
//                System.out.println(PATH+"/"+s);
//                File f=new File(PATH+"/"+s);
//                System.out.println(f.delete());
//
//            }

            //存储新图片
            PicFileUtil.saveAttracPic(attracpic,attraction);
        }
        else {

            //读取旧图
            String list=attractionService.getAttractionPicFileList(attraction.getAttracId());
            if(list!=null)
                attraction.setAttracPicFileList(list);
            else;//无图保持空
        }


        saveTypeAndCity(attraction,type,city);

        attractionService.updateAttraction(attraction);

        return "redirect:/attracMgr/loadAllAttractions";

    }

    @RequestMapping(value = "getAttracPic/{attracId}", produces="application/json")
    @ResponseBody
    public ResultInfo getAttracPic(@PathVariable String attracId) throws Exception{

        String fileList=attractionService.getAttractionPicFileList(Integer.parseInt(attracId));
        if(fileList!=null) {
            String[] fList = fileList.split("#");
            String path = "G:/FinalPrj/AttractionPic/";
            String encodePic=PicFileUtil.encodePicToBase64(fList);
            return new ResultInfo(true, "获取成功", encodePic);
        }
        return new ResultInfo(false, "没有图片", null);
    }

    @RequestMapping(value="getAttracType",produces="application/json")
    @ResponseBody
    public ResultInfo getAttracType(){
        List<AttractionType> result= attractionTypeService.loadAllAttracType();
        if(result!=null){
            return new ResultInfo(true,"获取成功",result);
        }
        else
            return new ResultInfo(false,"获取失败",null);
    }
    @RequestMapping(value="getCity",produces="application/json")
    @ResponseBody
    public ResultInfo getCity(){
        List<City> result=cityService.loadAllCity();
        if(result!=null){
            return new ResultInfo(true,"获取成功",result);
        }
        else
            return new ResultInfo(false,"获取失败",null);
    }

    @RequestMapping(value="deleteAttrac/{attracId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo deleteAttrac(@PathVariable  String attracId){

        try {
            Attraction attraction=new Attraction();
            attraction.setAttracId(Integer.parseInt(attracId));
            attractionService.deleteAttraction(attraction);
        }catch (Exception e){
            return new ResultInfo(false,e.getMessage(),null);
        }
        return new ResultInfo(true,"删除成功",null);
    }

    @RequestMapping(value="recoveryAttrac/{attracId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo recoveryAttrac(@PathVariable  String attracId){

        try {
            Attraction attraction=new Attraction();
            attraction.setAttracId(Integer.parseInt(attracId));
            attractionService.recoveryAttraction(attraction);
        }catch (Exception e){
            return new ResultInfo(false,e.getMessage(),null);
        }
        return new ResultInfo(true,"恢复成功",null);
    }



    @RequestMapping(value = "test")
    public String testPic() throws Exception{
        return "attrac_manage_page";
    }



    private void saveTypeAndCity(Attraction relatedAttrac,String attracTypeId,String cityId){

        Integer t=Integer.parseInt(attracTypeId);
        Integer c=Integer.parseInt(cityId);

        AttractionType at=new AttractionType();
        City ci=new City();

        at.setAttracTypeId(t);
        ci.setCityId(c);

        relatedAttrac.setAttracType(at);
        relatedAttrac.setAttracCity(ci);


    }

}
