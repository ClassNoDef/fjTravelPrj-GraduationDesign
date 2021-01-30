package com.travel.fj.service;

import com.travel.fj.domain.Attraction;
import com.travel.fj.queryhelper.AttracQueryHelper;
import com.travel.fj.utils.Page;

import java.util.List;

public interface AttractionService {

    void createAttraction(Attraction attraction);
    List<Attraction> loadAllAttractions();
    List<Attraction> loadQueryAttractions(AttracQueryHelper helper);
    Page loadQueryAndPagedAttractions(AttracQueryHelper attracQueryHelper, Page page);
    Page loadQueryAndPagedAttractionsForAdmin(AttracQueryHelper attracQueryHelper, Page page);
    Attraction getAttractionById(Integer id);
    String getAttractionPicFileList(Integer id);
    void updateAttraction(Attraction attraction);
    void updateAttracClickCount(Attraction attraction);
    List<Attraction> getPopularAttrac();
    List<Attraction> getRandomAttrac();
    void deleteAttraction(Attraction attraction);
    void recoveryAttraction(Attraction attraction);
}
