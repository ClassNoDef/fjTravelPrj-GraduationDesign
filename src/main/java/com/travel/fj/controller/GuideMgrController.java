package com.travel.fj.controller;

import com.travel.fj.domain.Admin;
import com.travel.fj.domain.Guide;
import com.travel.fj.domain.GuideType;
import com.travel.fj.queryhelper.GuideQueryHelper;
import com.travel.fj.queryhelper.WorkQueryHelper;
import com.travel.fj.service.AdminService;
import com.travel.fj.service.AttractionService;
import com.travel.fj.service.GuideService;
import com.travel.fj.service.GuideTypeService;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("guideMgr")
public class GuideMgrController {

    @Autowired
    GuideService guideService;

    @Autowired
    AttractionService attractionService;

    @Autowired
    GuideTypeService guideTypeService;

    @Autowired
    AdminService adminService;

    @GetMapping("createGuide/{attracId}")
    public String toCreateGuide(Model model, @PathVariable String attracId){
        Integer id=Integer.parseInt(attracId);
        Guide newGuide=new Guide();
        newGuide.setGuideAttrac(attractionService.getAttractionById(id));
        model.addAttribute("newGuide",newGuide);
        return "guide_page/guide_guide_create_page";
    }

    @PostMapping("createGuide")
    public String createGuide(Guide guide, String guidetype, MultipartFile[] guidePic, HttpSession session){
        Admin a=(Admin)session.getAttribute("logonAdmin");
        Admin author=adminService.getAdminByName(a.getAdminName());
        guide.setGuideAuthor(author);

        if(!guidePic[0].isEmpty()) {
            PicFileUtil.saveGuidePic(guidePic, guide);
        }

        GuideType g=new GuideType();
        g.setGuideTypeId(Integer.parseInt(guidetype));
        guide.setGuideType(g);

        guideService.createGuide(guide);

        return "redirect:/guideMgr/loadAllGuides";

    }

    @RequestMapping("loadAllGuides")
    public String loadAllGuides(Model model){

        GuideQueryHelper checkedGuides=new GuideQueryHelper();
        checkedGuides.setQueryGuideStatus(1);

        GuideQueryHelper noCheckGuides=new GuideQueryHelper();
        noCheckGuides.setQueryGuideStatus(0);

        GuideQueryHelper blockedGuides=new GuideQueryHelper();
        blockedGuides.setQueryGuideStatus(2);

        model.addAttribute("checkedGuidesList",guideService.loadQueryGuides(checkedGuides));
        model.addAttribute("noCheckGuidesList",guideService.loadQueryGuides(noCheckGuides));
        model.addAttribute("blockedGuidesList",guideService.loadQueryGuides(blockedGuides));

        return "guide_page/guide_guide_mgr_page";
    }

    @PostMapping("loadAllGuidesAjax")
    @ResponseBody
    public Page loadAllGuidesAjax(GuideQueryHelper gqData){
        Page p=new Page();
        System.out.println(gqData.getCurrentPageNum());
        p.setPageNo(gqData.getCurrentPageNum());
        Page r=guideService.loadQueryAndPagedGuides(gqData,p);
        return  r;
    }

    @RequestMapping("guideDetail/{guideId}")
    public String guideDetail(Model model,@PathVariable String guideId){


        model.addAttribute("guideDetail",guideService.getGuideById(Integer.parseInt(guideId)));
        return  "guide_page/guide_detail_page_for_check";

    }

    @RequestMapping(value = "getGuidePic/{guideId}", produces="application/json")
    @ResponseBody
    public ResultInfo getGuidePic(@PathVariable String guideId) throws Exception{

        String fileList=guideService.getGuidePicById(Integer.parseInt(guideId));
        if(fileList!=null) {
            String[] fList = fileList.split("#");
            String encodePic=PicFileUtil.encodePicToBase64(fList);
            return new ResultInfo(true, "获取成功", encodePic);
        }
        return new ResultInfo(false, "没有图片", null);
    }

    @RequestMapping(value="getGuideType",produces = "application/json")
    @ResponseBody
    public ResultInfo getGuideType(){
        List<GuideType> result=guideTypeService.loadAllGuideType();
        if(result!=null){
            return new ResultInfo(true,"获取成功",result);
        }
        return  new ResultInfo(false,"获取失败",null);
    }

    @GetMapping("updateGuide/{guideId}")
    public String toUpdateWork(Model model,HttpSession session,@PathVariable String guideId){

            model.addAttribute("preUpdateGuide",guideService.getGuideById(Integer.parseInt(guideId)));
            return "guide_page/guide_update_page";

    }

    @PostMapping("updateGuide")
    public String updateWork(Guide guide, String guidetype, MultipartFile[] guidePic, HttpSession session){

        //回填作者
        Admin a=(Admin) session.getAttribute("logonAdmin") ;
        Admin author=adminService.getAdminByName(a.getAdminName());
        guide.setGuideAuthor(author);


        //回填日期
        guide.setGuideCreateTime(guideService.getGuideById(guide.getGuideId()).getGuideCreateTime());

        if(!guidePic[0].isEmpty()) {
            PicFileUtil.saveGuidePic(guidePic, guide);
        }

        else{
            String list=guideService.getGuidePicById(guide.getGuideId());
            if(list!=null){
                guide.setGuidePicList(list);
            }
            else;
        }


        GuideType G=new GuideType();
        G.setGuideTypeId(Integer.parseInt(guidetype));
        guide.setGuideType(G);

        guideService.updateGuide(guide);
        return "redirect:/guideMgr/loadAllGuides";
    }

    @GetMapping("changeGuideStatus/{guideId}")
    public String changeStatus(@PathVariable String guideId,Model model){
        try{
            Integer.parseInt(guideId);
        }catch(NumberFormatException e){
            return "error_page/work_access_error_page";
        }

        if(!isValidGuide(Integer.parseInt(guideId))){

            return "error_page/work_access_error_page";
        }
        Guide detail=guideService.getGuideById(Integer.parseInt(guideId));
        model.addAttribute("guideDetail",detail);

        return "guide_page/guide_detail_page_for_check";
    }

    @PostMapping("changeGuideStatus")
    public String changeStatus(String id,String status){
        Guide guide=new Guide();
        guide.setGuideId(Integer.parseInt(id));
        guide.setGuideStatus(Integer.parseInt(status));


        guideService.updateGuideStatus(guide);
        return  "redirect:/guideMgr/loadAllGuides";
    }

    @RequestMapping("test")
    public String test(){
        return "guide_page/guide_test";
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
