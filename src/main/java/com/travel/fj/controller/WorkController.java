package com.travel.fj.controller;

import com.travel.fj.domain.LikeObject;
import com.travel.fj.domain.NoteType;
import com.travel.fj.domain.User;
import com.travel.fj.domain.Work;
import com.travel.fj.queryhelper.WorkQueryHelper;
import com.travel.fj.service.*;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.PicFileUtil;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("work")
public class WorkController {

    @Autowired
    NoteTypeService noteTypeService;

    @Autowired
    AttractionService attractionService;

    @Autowired
    WorkService workService;

    @Autowired
    UserService userService;

    @Autowired
    LikeObjectService likeObjectService;

    @GetMapping("createWork/{attracId}")
    public String toCreateWork(Model model, @PathVariable String attracId){
        Integer id=Integer.parseInt(attracId);
        Work newWork=new Work();
        newWork.setWorkAttrac(attractionService.getAttractionById(id));
        model.addAttribute("newWork",newWork);
        return "work_page/work_create_page";
    }

    @PostMapping("createWork")
    public String createWork(Work work, String noteType, MultipartFile[] workPic, HttpSession session){

        User u=(User)session.getAttribute("logonUser") ;
        User author=userService.getUserByName(u.getUserName());
        work.setWorkAuthor(author);

        if(!workPic[0].isEmpty()) {
            PicFileUtil.saveWorkPic(workPic, work);
        }

        Integer n=Integer.parseInt(noteType);
        NoteType N=new NoteType();
        N.setNoteTypeId(n);
        work.setWorkType(N);

        workService.createWork(work);
        return "redirect:/user/userCenter";
    }

    @RequestMapping("myWork")
    public String loadUserWorks(Model model,HttpSession session){
        User u=(User)session.getAttribute("logonUser") ;
        User author=userService.getUserByName(u.getUserName());
        Integer authorId=author.getUserId();

        model.addAttribute("userWorks",workService.getWorkByUserId(authorId));

        return "work_page/work_my_work_page";

    }

    @RequestMapping("workDetail/{workId}")
    public String workDetail(Model model,HttpSession session,@PathVariable String workId){

        model.addAttribute("isLike",false);
        try{
            Integer.parseInt(workId);
        }catch(NumberFormatException e){
            return "error_page/work_access_error_page";
        }

        if(!isValidWork(Integer.parseInt(workId))){

            return "error_page/work_access_error_page";
        }

        User u=(User)session.getAttribute("logonUser");
        if(u!=null){
            LikeObject lo=likeObjectService.getUserLikeRecordByUserIdAndWorkId(u.getUserId(),
                    Integer.parseInt(workId));
            if(lo!=null){
                model.addAttribute("isLike",true);
            }
            else
                model.addAttribute("isLike",false);
        }

        Work detail=workService.getWorkById(Integer.parseInt(workId));
        List<Work> otherLinkWork=workService.getWorkByAttracIdU(detail.getWorkAttrac().getAttracId());
        model.addAttribute("otherLinkWork",otherLinkWork);
        model.addAttribute("workDetail",detail);
        return "work_page/work_detail_page";

    }

    @RequestMapping("userWorkDetail/{workId}")
    public String userWorkDetail(Model model,@PathVariable String workId){

        try{
            Integer.parseInt(workId);
        }catch(NumberFormatException e){
            return "error_page/work_access_error_page";
        }

        if(!isValidWork(Integer.parseInt(workId))){

            return "error_page/work_access_error_page";
        }
        Work detail=workService.getWorkById(Integer.parseInt(workId));
//        List<Work> otherLinkWork=workService.getWorkByAttracId(detail.getWorkAttrac().getAttracId());
//        model.addAttribute("otherLinkWork",otherLinkWork);
        model.addAttribute("workDetail",detail);
        return "work_page/work_detail_page_for_user";

    }



    @GetMapping("updateWork/{workId}")
    public String toUpdateWork(Model model,@PathVariable String workId,HttpSession session ){
        if(!isValidAndSelfWork(Integer.parseInt(workId),session)){
            return "error_page/work_access_error_page";
        }
        model.addAttribute("preUpdateWork",workService.getWorkById(Integer.parseInt(workId)));
        return  "work_page/work_update_page";
    }

    @PostMapping("updateWork")
    public String updateWork(Work work, String notetype, MultipartFile[] workPic, HttpSession session){

        //回填作者
        User u=(User) session.getAttribute("logonUser") ;
        User author=userService.getUserByName(u.getUserName());
        work.setWorkAuthor(author);


        //回填日期
        work.setWorkCreateTime(workService.getWorkById(work.getWorkId()).getWorkCreateTime());

        if(!workPic[0].isEmpty()) {
            PicFileUtil.saveWorkPic(workPic, work);
        }

        else{
            String list=workService.getWorkPicById(work.getWorkId());
            if(list!=null){
                work.setWorkPicList(list);
            }
            else;
        }



        NoteType G=new NoteType();
        G.setNoteTypeId(Integer.parseInt(notetype));
        work.setWorkType(G);

        workService.updateWork(work);
        return "redirect:/work/myWork";
    }

    @PostMapping("deleteMyWork/{workId}")
    @ResponseBody
    public ResultInfo deleteMyWork(@PathVariable String workId){
        try {
            Work w = workService.getWorkById(Integer.parseInt(workId));
            w.setWorkStatus(3);
            workService.updateWorkStatus(w);
        }catch (Exception e){
            return new ResultInfo(false,"删除失败",null);
        }
        return new ResultInfo(true,"删除成功",null);
    }

    //获取随机游记，实现首页随机游记 部分刷新
    @RequestMapping(value="randomWorkAjax",produces = "application/json")
    @ResponseBody
    public List<Work>  randomWorkAjax(){
        //使用Service层的方法进行查找
        List<Work> r=workService.getRandomWorkU();

        return r;
    }

    @RequestMapping("showWork/{workTypeId}")
    public String showWorkByNoteTypeId(@PathVariable String workTypeId, Model model){
        Integer typeId;
        try {
            //防止输入非数字
            typeId=Integer.parseInt(workTypeId);
        }catch(NumberFormatException e){
            //返回错误页面
            return "error_page/work_list_access_error_page";
        }
        //输入错误的ID，即无法找到相应类型时
        if(noteTypeService.getNoteTypeById(typeId)==null){
            //返回错误页面
            return "error_page/work_list_access_error_page";
        }

        //查找到类型信息后将ID保存到model，在页面利用Ajax向搜索用方法searchAttrac()查找
        model.addAttribute("queryWorkType", typeId);
        return "work_page/work_show_work_by_work_type_id";
    }

    @GetMapping("showWork")
    public String showWorkByKeyword(String keyword,Model model){
        model.addAttribute("queryWorkTitle", keyword);
        return "work_page/work_show_work_by_keyword";
    }

    @PostMapping(value = "searchWork",produces="application/json")
    @ResponseBody                       //spring自动将post提交的数据进行封装
    public Page searchWork( WorkQueryHelper qData){

        Page p=new Page();
        p.setPageNo(qData.getCurrentPageNum());
        //使用Service层的组合查询方法进行查找
        Page result=workService.loadQueryAndPagedWorks(qData,p);

        //返回结果
        return result;
    }

    @RequestMapping(value="getWorkSinglePic/{workId}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getAttracSinglePic(@PathVariable String workId){
        String picList=workService.getWorkPicById(Integer.parseInt(workId));

        if(picList!=null){
            String[] l=picList.split("#");
            return  PicFileUtil.getFirstPic(l);
        }
        else
            return null;
    }

    @RequestMapping(value = "getWorkPic/{workId}", produces="application/json")
    @ResponseBody
    public ResultInfo getWorkPic(@PathVariable String workId) throws Exception{

        String fileList=workService.getWorkPicById(Integer.parseInt(workId));
        if(fileList!=null) {
            String[] fList = fileList.split("#");
            String encodePic=PicFileUtil.encodePicToBase64(fList);
            return new ResultInfo(true, "获取成功", encodePic);
        }
        return new ResultInfo(false, "没有图片", null);
    }



    @RequestMapping(value="getNoteType",produces="application/json")
    @ResponseBody
    public ResultInfo getNoteType(){
        List<NoteType> result= noteTypeService.loadAllNoteType();
        if(result!=null){
            return new ResultInfo(true,"获取成功",result);
        }
        else
            return new ResultInfo(false,"获取失败",null);
    }



    private boolean isValidAndSelfWork(Integer workId,HttpSession session){

        //验证作品是否存在
        Work preCheck=workService.getWorkById(workId);
        if(preCheck==null){
            return false;
        }

        //是否为登录用户自己的作品
        User  workAuthor=preCheck.getWorkAuthor();
        User  u=(User)session.getAttribute("logonUser") ;

        return workAuthor.getUserName().equals(u.getUserName());
    }

    private boolean isValidWork(Integer workId){
        Work preCheck=workService.getWorkById(workId);
        if(preCheck!=null){
            return true;
        }
        else
            return false;
    }





}
