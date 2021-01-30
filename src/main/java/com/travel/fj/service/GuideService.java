package com.travel.fj.service;

import com.travel.fj.domain.Guide;
import com.travel.fj.queryhelper.GuideQueryHelper;
import com.travel.fj.utils.Page;

import java.util.List;

public interface GuideService {

    void createGuide(Guide guide);
    List<Guide> loadAllGuides();
    List<Guide> loadQueryGuides(GuideQueryHelper helper);
    Page loadQueryAndPagedGuides
            (GuideQueryHelper helper, Page page);
    Integer getQueryGuideCount(GuideQueryHelper helper);
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
