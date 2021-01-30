package com.travel.fj.controller;

import com.travel.fj.domain.LikeObject;
import com.travel.fj.domain.User;
import com.travel.fj.domain.Work;
import com.travel.fj.service.LikeObjectService;
import com.travel.fj.service.WorkService;
import com.travel.fj.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("likeMgr")
public class LikeController {

    @Autowired
    LikeObjectService likeObjectService;

    @Autowired
    WorkService workService;

    @PostMapping("like")
    @ResponseBody
    public ResultInfo like(HttpSession session, String workId){
        User logonUser=(User)session.getAttribute("logonUser");
        Integer wid;
        try{
            wid=Integer.parseInt(workId);
        }catch (NumberFormatException e){
            return new ResultInfo(false,"无效ID",null);
        }
        LikeObject lo=likeObjectService.getUserLikeRecordByUserIdAndWorkId(logonUser.getUserId(),wid);
        if(lo!=null){
            return new ResultInfo(false,"已经点过赞",null);
        }
        else{
            LikeObject newRecord=new LikeObject(logonUser.getUserId(),wid);
            likeObjectService.createLikeRecord(newRecord);
            Work countUpd=new Work();
            countUpd.setWorkId(wid);
            countUpd.setWorkLikeCount(workService.getWorkById(wid).getWorkLikeCount()+1);
            workService.updateLikeCount(countUpd);
            return new ResultInfo(true,"点赞成功",null);
        }
    }

    @PostMapping("disLike")
    @ResponseBody
    public ResultInfo disLike(HttpSession session, String workId){
        Integer wid;
        User logonUser=(User)session.getAttribute("logonUser");

        try{
            Integer.parseInt(workId);
        }catch (NumberFormatException e){
            return new ResultInfo(false,"无效ID",null);
        }

        wid=Integer.parseInt(workId);

        LikeObject lo=likeObjectService.getUserLikeRecordByUserIdAndWorkId(logonUser.getUserId(),wid);
        if(lo==null){
            return new ResultInfo(false,"没有点过赞，无需取消点赞",null);
        }
        else{

            likeObjectService.deleteLikeRecordById(lo.getLikeId());
            Work countUpd=new Work();
            countUpd.setWorkId(wid);
            countUpd.setWorkLikeCount(workService.getWorkById(wid).getWorkLikeCount()-1);
            workService.updateLikeCount(countUpd);
            return new ResultInfo(true,"取消点赞成功",null);
        }
    }

}
