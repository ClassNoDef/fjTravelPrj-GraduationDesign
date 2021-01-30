package com.travel.fj.controller;

import com.travel.fj.domain.NoteType;
import com.travel.fj.domain.Work;
import com.travel.fj.queryhelper.WorkQueryHelper;
import com.travel.fj.service.NoteTypeService;
import com.travel.fj.service.WorkService;
import com.travel.fj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("workMgr")
public class WorkMgrController {

    @Autowired
    WorkService workService;

    @Autowired
    NoteTypeService noteTypeService;

    @RequestMapping("loadAllWorks")
    public String loadAllWorks(Model model){

//        WorkQueryHelper checkedWorks=new WorkQueryHelper();
//        checkedWorks.setQueryWorkStatus(1);
//        WorkQueryHelper noCheckWorks=new WorkQueryHelper();
//        noCheckWorks.setQueryWorkStatus(0);
//        WorkQueryHelper blockedWorks=new WorkQueryHelper();
//        blockedWorks.setQueryWorkStatus(2);
//          model.addAttribute("checkedWorks",workService.loadQueryWorks(checkedWorks));
//          model.addAttribute("noCheckWorks",workService.loadQueryWorks(noCheckWorks));
//          model.addAttribute("blockedWorks",workService.loadQueryWorks(blockedWorks));

//        model.addAttribute("checkedWorks",workService.loadCheckedWorks());
//        model.addAttribute("noCheckWorks",workService.loadNoCheckWorks());
//        model.addAttribute("blockedWorks",workService.loadBlockedWorks());

        return "work_page/work_work_mgr_page";
    }

    @PostMapping("loadAllWorksAjax")
    @ResponseBody
    public Page loadAllWorksAjax(WorkQueryHelper wqData){
        Page p=new Page();
        System.out.println(wqData.getCurrentPageNum());
        p.setPageNo(wqData.getCurrentPageNum());
        Page r=workService.loadQueryAndPagedWorksAdmin(wqData,p);
        return  r;
    }


    @GetMapping("workCheck/{workId}")
    public String workCheck(Model model, @PathVariable String workId){
        model.addAttribute("checkWorks",workService.getWorkByIdAdmin(Integer.parseInt(workId)));

        return "work_page/work_detail_page_for_check";
    }

    @PostMapping("workCheck")
    public String workCheck(String id,String status){
        Work work=new Work();
        work.setWorkId(Integer.parseInt(id));
        work.setWorkStatus(Integer.parseInt(status));

        workService.updateWorkStatus(work);
        return "redirect:/workMgr/loadAllWorks";
    }

    @RequestMapping(value="getNoteType",produces="application/json")
    @ResponseBody
    public List<NoteType> getNoteType(){
        List<NoteType> result= noteTypeService.loadAllNoteType();

        return result;
    }

    @RequestMapping(value="fytest",produces="application/json")
    @ResponseBody
    public Map test(){
        Map<String,Object> result=new HashMap<>();
        result.put("length",workService.getRandomWorkU().size());
        result.put("data",workService.getRandomWorkU());
        return result;
    }

    @RequestMapping("test")
    public String ttest(){
        return "work_page/work_work_mgr_page_test";
    }
}
