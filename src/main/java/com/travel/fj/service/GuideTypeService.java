package com.travel.fj.service;

import com.travel.fj.domain.GuideType;

import java.util.List;

public interface GuideTypeService {

    void createGuideType(GuideType guideType);
    List<GuideType> loadAllGuideType();
    GuideType getGuideTypeById(int id);
    void updateGuideType(GuideType guideType);
    void deleteGuideType(int id);
    void updateGuideCountData(GuideType guideType);
}
