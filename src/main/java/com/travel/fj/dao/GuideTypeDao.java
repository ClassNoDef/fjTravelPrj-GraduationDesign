package com.travel.fj.dao;

import com.travel.fj.domain.GuideType;

import java.util.List;

public interface GuideTypeDao {
    void createGuideType(GuideType guideType);
    List<GuideType> loadAllGuideType();
    GuideType getGuideTypeById(int id);
    void updateGuideType(GuideType guideType);
    void updateGuideCountData(GuideType guideType);
    void deleteGuideType(int id);
}
