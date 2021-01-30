package com.travel.fj.dao;

import com.travel.fj.domain.Attraction;
import com.travel.fj.queryhelper.AttracQueryHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AttractionDao {
    void createAttraction(Attraction attraction);
    List<Attraction> loadAllAttractions();
    List<Attraction> loadQueryAttractions(@Param("helper") AttracQueryHelper attracQueryHelper);
    Integer getQueryAttracCount(@Param("helper") AttracQueryHelper helper);
    List<Attraction> loadQueryAndPagedAttractions
            (@Param("helper") AttracQueryHelper attracQueryHelper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Integer getQueryAttracCountForAdmin(@Param("helper") AttracQueryHelper helper);
    List<Attraction> loadQueryAndPagedAttractionsForAdmin
            (@Param("helper") AttracQueryHelper attracQueryHelper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Attraction getAttractionById(Integer id);
    Attraction getAttractionByIdIgnoreBlocked(Integer id);
    String getAttractionPicFileList(Integer id);
    void updateAttraction(Attraction attraction);
    void updateAttractionData(Attraction attraction);
    void updateAttracClickCount(Attraction attraction);
    List<Attraction> getPopularAttrac();
    List<Attraction> getRandomAttrac();
    void deleteAttraction(Attraction attraction);
    void recoveryAttraction(Attraction attraction);
}
