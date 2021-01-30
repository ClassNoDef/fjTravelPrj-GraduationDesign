package com.travel.fj.controller;

import com.travel.fj.domain.AttractionType;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.service.AttractionTypeService;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("attracTypeMgr")
public class AttracTypeManageController {

    @Autowired
    AttractionTypeService attractionTypeService;
    @GetMapping("createAttracType")
    public String toCreateAttracType(Model model){
        model.addAttribute("newAttracType",new AttractionType());
        return "attrac_type_page/attrac_type_create_page";
    }

    @PostMapping("createAttracType")
    public  String createAttracType(AttractionType newAttracType,Model model){
        try {
            attractionTypeService.createAttracType(newAttracType);
        }catch (DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("newAttracType",new AttractionType());
            return "attrac_type_page/attrac_type_create_page";
        }
        return  "redirect:/attracTypeMgr/loadAllAttracType";
    }

    @RequestMapping("loadAllAttracType")
    public String loadAllAttracType(Model model) {
        model.addAttribute("loadedAttracType",attractionTypeService.loadAllAttracType());
        return "attrac_type_page/attrac_type_manage_page";
    }

    @PostMapping("loadAlAttracTypeAjax")
    @ResponseBody
    public List<AttractionType> loadAllAttracTypeAjax(){
        List<AttractionType> result=attractionTypeService.loadAllAttracType();
        return result;
    }

    @GetMapping("updateAttracType/{attracTypeId}")
    public String toUpdateAttracType(@PathVariable String attracTypeId,Model model){
        Integer id=Integer.parseInt(attracTypeId);
        model.addAttribute("preUpdateAttracType",attractionTypeService.getAttracTypeById(id));
        return "attrac_type_page/attrac_type_update_page";
    }

    @PostMapping("updateAttracType")
    public  String updateAttracType(AttractionType attractionType ,Model model){
        try{
            attractionTypeService.updateAttracType(attractionType);
        }catch(DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("preUpdateAttracType",attractionTypeService.getAttracTypeById(attractionType.getAttracTypeId()));
            return "attrac_type_page/attrac_type_update_page";
        }
        return "redirect:/attracTypeMgr/loadAllAttracType";
    }

    @RequestMapping(value = "deleteAttracType/{attracTypeId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo deleteAttracType(@PathVariable String attracTypeId){
            Integer id=Integer.parseInt(attracTypeId);
            try {
                attractionTypeService.deleteAttracType(id);
            }catch (DataAccessException e){

                return new ResultInfo(false,e.getMessage(),null);
            }

            return new ResultInfo(true,"删除成功",null);
    }
}
