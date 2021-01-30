package com.travel.fj.service;

import com.travel.fj.domain.Work;
import com.travel.fj.queryhelper.WorkQueryHelper;
import com.travel.fj.utils.Page;

import java.util.List;

public interface WorkService {

    void createWork(Work work);
    List<Work> loadCheckedWorks();
    List<Work> loadNoCheckWorks();
    List<Work> loadBlockedWorks();
    List<Work> loadQueryWorks(WorkQueryHelper helper);
    Page loadQueryAndPagedWorks
            (WorkQueryHelper workQueryHelper, Page page);
    Page loadQueryAndPagedWorksAdmin
            (WorkQueryHelper workQueryHelper, Page page);
    Work getWorkById(int id);
    Work getWorkByIdAdmin(int id);
    List<Work> getWorkByUserId(int userId);//一个用户名下可能有多个作品
    List<Work> getWorkByWorkTypeId(int typeId);//一个类型下可能有多个作品
    List<Work> getWorkByAttracId(int AttracId);
    List<Work> getWorkByAttracIdU(int AttracId);
    List<Work> getRandomWork();
    List<Work> getRandomWorkU();
    List<Work> getPopularWork();
    List<Work> getPopularWorkU();
    String getWorkPicById(int id);
    void updateWork(Work work);
    void updateWorkStatus(Work work);
    void updateLikeCount(Work work);
    void blockFreezeUserWork(int uid);
    void unBlockFreezeUserWork(int uid);
}
