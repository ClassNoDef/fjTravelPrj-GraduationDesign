package com.travel.fj.service;

import com.travel.fj.dao.*;
import com.travel.fj.domain.*;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.queryhelper.AttracQueryHelper;
import com.travel.fj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    @Autowired
    AttractionDao attractionDao;

    @Autowired
    AttractionTypeDao attractionTypeDao;

    @Autowired
    CityDao cityDao;

    @Autowired
    WorkDao workDao;

    @Autowired
    GuideDao guideDao;

    @Override
    public void createAttraction(Attraction attraction) {
        try {
            attractionDao.createAttraction(attraction);
        }catch (Exception e){
            if(e.getMessage().contains("attrac_name_UNIQUE")){
                throw new DataAccessException("景点名称重复！");
            }
        }
        AttractionType a=attractionTypeDao.getAttracTypeById(attraction.getAttracType()
                .getAttracTypeId());
        a.setAttracCount(a.getAttracCount()+1);
        attractionTypeDao.updateAttracCountData(a);

       City c=cityDao.getCityById(attraction.getAttracCity().getCityId());
       c.setAttracCount(c.getAttracCount()+1);
       cityDao.updateAttracCountData(c);

    }

    @Override
    public List<Attraction> loadAllAttractions() {
        return attractionDao.loadAllAttractions();
    }

    @Override
    public List<Attraction> loadQueryAttractions(AttracQueryHelper helper) {
        return attractionDao.loadQueryAttractions(helper);
    }

    @Override
    public Page loadQueryAndPagedAttractions(AttracQueryHelper attracQueryHelper, Page page) {
         page.setPageContent(attractionDao.loadQueryAndPagedAttractions(attracQueryHelper,page.getStartIndex(),page.getPageSize()));
         page.setTotalRecNum((long)attractionDao.getQueryAttracCount(attracQueryHelper));
         return page;
    }

    @Override
    public Page loadQueryAndPagedAttractionsForAdmin(AttracQueryHelper attracQueryHelper, Page page) {
        page.setPageContent(attractionDao.loadQueryAndPagedAttractionsForAdmin(attracQueryHelper,page.getStartIndex(),page.getPageSize()));
        page.setTotalRecNum((long)attractionDao.getQueryAttracCountForAdmin(attracQueryHelper));
        return page;
    }

    @Override
    public Attraction getAttractionById(Integer id) {
        return attractionDao.getAttractionById(id);
    }

    @Override
    public String getAttractionPicFileList(Integer id) {
        String result=attractionDao.getAttractionPicFileList(id);

        return result;
    }

    @Override
    public void updateAttraction(Attraction attraction) {
        //获取所属城市/景点类型数据以便更新
        //一定要在更新之前获取旧所在城市/景点类型ID，通过景点ID取出其所在城市/景点类型的原始数据
        City oldCity=attractionDao.getAttractionById(attraction.getAttracId()).getAttracCity();
        AttractionType oldType=attractionDao.getAttractionById(attraction.getAttracId()).getAttracType();


        //执行更新操作
        try {
            attractionDao.updateAttraction(attraction);
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(e.getMessage().contains("attrac_name_UNIQUE")){
                throw new DataAccessException("景点名称重复！");
            }
        }

        //更新旧城市/景点类型数据
        oldCity.setAttracCount(oldCity.getAttracCount()-1);
        cityDao.updateAttracCountData(oldCity);
        oldType.setAttracCount(oldType.getAttracCount()-1);
        attractionTypeDao.updateAttracCountData(oldType);

        //获取新城市/景点类型数据，直接从传进来的更新变量获取，一定要在旧数据更新完后取，防止在数据未改变时出错
        City newCity=cityDao.getCityById(attraction.getAttracCity().getCityId());
        AttractionType newType=attractionTypeDao.getAttracTypeById(attraction.getAttracType().getAttracTypeId());

        //更新新城市/景点类型数据
        newCity.setAttracCount(newCity.getAttracCount()+1);
        cityDao.updateAttracCountData(newCity);
        newType.setAttracCount(newType.getAttracCount()+1);
        attractionTypeDao.updateAttracCountData(newType);
    }

    @Override
    public void updateAttracClickCount(Attraction attraction) {
        attractionDao.updateAttracClickCount(attraction);
    }

    @Override
    public List<Attraction> getPopularAttrac() {
        return attractionDao.getPopularAttrac();
    }

    @Override
    public List<Attraction> getRandomAttrac() {
        return attractionDao.getRandomAttrac();
    }

    @Override
    public void deleteAttraction(Attraction attraction) {

        //下架景点操作
        attractionDao.deleteAttraction(attraction);

        //下架相关游记
        List<Work> wList=workDao.getWorkByAttracId(attraction.getAttracId()) ;

        for(Work w:wList){
            w.setWorkStatus(2);
            workDao.updateWorkStatus(w);
        }

        //下架相关指南
        List<Guide> gList=guideDao.getGuideByAttracId(attraction.getAttracId());
        for(Guide g:gList){
            g.setGuideStatus(2);
            guideDao.updateGuideStatus(g);
        }

    }

    @Override
    public void recoveryAttraction(Attraction attraction) {
        attractionDao.recoveryAttraction(attraction);

        List<Work> wList=workDao.getWorkByAttracId(attraction.getAttracId()) ;

        for(Work w:wList){
            w.setWorkStatus(0);
            workDao.updateWorkStatus(w);
        }

        //上架相关指南
        List<Guide> gList=guideDao.getGuideByAttracId(attraction.getAttracId());
        for(Guide g:gList){
            g.setGuideStatus(0);
            guideDao.updateGuideStatus(g);
        }

    }
}
