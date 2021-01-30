package com.travel.fj.controller;

import com.travel.fj.domain.AttractionType;
import com.travel.fj.domain.GuideType;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.service.AttractionTypeService;
import com.travel.fj.service.GuideTypeService;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("guideTypeMgr")
public class GuideTypeManageController {

    @Autowired
    GuideTypeService guideTypeService;
    @GetMapping("createGuideType")
    public String toCreateGuideType(Model model){
        model.addAttribute("newGuideType",new GuideType());
        return "guide_type_page/guide_type_create_page";
    }

    @PostMapping("createGuideType")
    public  String createGuideType(GuideType newGuideType,Model model){
        try {
            guideTypeService.createGuideType(newGuideType);
        }catch (DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("newGuideType",new GuideType());
            return "guide_type_page/guide_type_create_page";
        }
        return  "redirect:/guideTypeMgr/loadAllGuideType";
    }

    @RequestMapping("loadAllGuideType")
    public String loadAllGuideType(Model model) {
        model.addAttribute("loadedGuideType",guideTypeService.loadAllGuideType());
        return "guide_type_page/guide_type_manage_page";
    }

    @PostMapping("loadAllGuideTypeAjax")
    @ResponseBody
    public List<GuideType> loadAllGuideTypeAjax(){
        List<GuideType> result=guideTypeService.loadAllGuideType();
        return result;
    }


    @GetMapping("updateGuideType/{guideTypeId}")
    public String toUpdateGuideType(@PathVariable String guideTypeId,Model model){
        Integer id=Integer.parseInt(guideTypeId);
        model.addAttribute("preUpdateGuideType",guideTypeService.getGuideTypeById(id));
        return "guide_type_page/guide_type_update_page";
    }

    @PostMapping("updateGuideType")
    public  String updateGuideType(GuideType guideType ,Model model){
        try{
            guideTypeService.updateGuideType(guideType);
        }catch(DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("preUpdateGuideType",guideTypeService.getGuideTypeById(guideType.getGuideTypeId()));
            return "guide_type_page/guide_type_update_page";
        }
        return "redirect:/guideTypeMgr/loadAllGuideType";
    }

    @RequestMapping(value = "deleteGuideType/{guideTypeId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo deleteGuideType(@PathVariable String guideTypeId){
            Integer id=Integer.parseInt(guideTypeId);
            try {
               guideTypeService.deleteGuideType(id);
            }catch (DataAccessException e){

                return new ResultInfo(false,e.getMessage(),null);
            }

            return new ResultInfo(true,"删除成功",null);
    }
}
