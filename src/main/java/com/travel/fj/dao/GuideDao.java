package com.travel.fj.dao;

import com.travel.fj.domain.Guide;
import com.travel.fj.queryhelper.GuideQueryHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuideDao {
    void createGuide(Guide guide);
    List<Guide> loadAllGuides();
    List<Guide> loadQueryGuides(@Param("helper")GuideQueryHelper helper);
    List<Guide> loadQueryAndPagedGuides
            (@Param("helper")GuideQueryHelper helper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Integer getQueryGuideCount(@Param("helper")GuideQueryHelper helper);
    Guide getGuideById(Integer id);
    List<Guide> getGuideByAdminId(Integer id);
    List<Guide> getGuideByGuideTypeId(Integer id);
    List<Guide> getGuideByGuideTypeIdU(Integer id);
    List<Guide> getGuideByAttracId(Integer attracId);
    List<Guide> getGuideByAttracIdU(Integer attracId);
    List<Guide> getRandomGuide();
    List<Guide> getRandomGuideU();
    String getGuidePicById(Integer id);
    void updateGuide(Guide guide);
    void  updateGuideStatus(Guide guide);
    void updateGuideClickCount(Guide guide);
}
