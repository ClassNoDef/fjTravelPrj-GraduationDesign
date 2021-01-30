package com.travel.fj.controller;

import com.travel.fj.domain.NoteType;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.service.NoteTypeService;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("noteTypeMgr")
public class NoteTypeManageController {

    @Autowired
    NoteTypeService noteTypeService;
    @GetMapping("createNoteType")
    public String toCreateNoteType(Model model){
        model.addAttribute("newNoteType",new NoteType());
        return "note_type_page/note_type_create_page";
    }

    @PostMapping("createNoteType")
    public  String createNoteType(NoteType newNoteType,Model model){
        try {
            noteTypeService.createNoteType(newNoteType);
        }catch (DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("newNoteType",new NoteType());
            return "note_type_page/note_type_create_page";
        }
        return  "redirect:/noteTypeMgr/loadAllNoteType";
    }

    @RequestMapping("loadAllNoteType")
    public String loadAllNoteType(Model model) {
        model.addAttribute("loadedNoteType",noteTypeService.loadAllNoteType());
        return "note_type_page/note_type_manage_page";
    }

    @PostMapping("loadAllNoteTypeAjax")
    @ResponseBody
    public List<NoteType> loadAllNoteTypeAjax(){
        List<NoteType> result=noteTypeService.loadAllNoteType();
        return result;
    }

    @GetMapping("updateNoteType/{noteTypeId}")
    public String toUpdateNoteType(@PathVariable String noteTypeId,Model model){
        Integer id=Integer.parseInt(noteTypeId);
        model.addAttribute("preUpdateNoteType",noteTypeService.getNoteTypeById(id));
        return "note_type_page/note_type_update_page";
    }

    @PostMapping("updateNoteType")
    public  String updateNoteType(NoteType noteType ,Model model){
        try{
            noteTypeService.updateNoteType(noteType);
        }catch(DataAccessException e){
            model.addAttribute("errmsg",e.getMessage());
            model.addAttribute("preUpdateNoteType",noteTypeService.getNoteTypeById(noteType.getNoteTypeId()));
            return "note_type_page/note_type_update_page";
        }
        return "redirect:/noteTypeMgr/loadAllNoteType";
    }

    @RequestMapping(value = "deleteNoteType/{noteTypeId}" ,produces="application/json")
    @ResponseBody
    public ResultInfo deleteNoteType(@PathVariable String noteTypeId){
            Integer id=Integer.parseInt(noteTypeId);
            try {
               noteTypeService.deleteNoteType(id);
            }catch (DataAccessException e){

                return new ResultInfo(false,e.getMessage(),null);
            }

            return new ResultInfo(true,"删除成功",null);
    }
}
