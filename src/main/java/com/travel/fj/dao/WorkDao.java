package com.travel.fj.dao;

import com.travel.fj.domain.Work;
import com.travel.fj.queryhelper.WorkQueryHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkDao {
    void createWork(Work work);
    List<Work> loadCheckedWorks();
    List<Work> loadNoCheckWorks();
    List<Work> loadBlockedWorks();
    List<Work> loadQueryWorks(@Param("helper")WorkQueryHelper helper);
    List<Work> loadQueryAndPagedWorks
            (@Param("helper") WorkQueryHelper workQueryHelper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Integer getQueryWorkCount(@Param("helper") WorkQueryHelper helper);
    List<Work> loadQueryAndPagedWorksAdmin
            (@Param("helper") WorkQueryHelper workQueryHelper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Integer getQueryWorkCountAdmin(@Param("helper") WorkQueryHelper helper);
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
