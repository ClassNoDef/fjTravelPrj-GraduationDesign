package com.travel.fj.service;

import com.travel.fj.dao.AttractionDao;
import com.travel.fj.dao.GuideDao;
import com.travel.fj.dao.GuideTypeDao;
import com.travel.fj.domain.Attraction;
import com.travel.fj.domain.Guide;
import com.travel.fj.domain.GuideType;
import com.travel.fj.queryhelper.GuideQueryHelper;
import com.travel.fj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    GuideDao guideDao;

    @Autowired
    GuideTypeDao guideTypeDao;

    @Autowired
    AttractionDao attractionDao;
    @Override
    public void createGuide(Guide guide) {
        guideDao.createGuide(guide);
        GuideType g=guideTypeDao.getGuideTypeById(guide.getGuideType().getGuideTypeId());
        g.setGuideCount(g.getGuideCount()+1);
        guideTypeDao.updateGuideCountData(g);

        Attraction a=attractionDao.getAttractionById(guide.getGuideAttrac().getAttracId());
        a.setAttracNoteCount(a.getAttracNoteCount()+1);
        attractionDao.updateAttractionData(a);
    }

    @Override
    public List<Guide> loadAllGuides() {
        return guideDao.loadAllGuides();
    }

    @Override
    public List<Guide> loadQueryGuides(GuideQueryHelper helper) {
        return guideDao.loadQueryGuides(helper);
    }

    @Override
    public Page loadQueryAndPagedGuides(GuideQueryHelper helper, Page page) {
        page.setPageContent(guideDao.loadQueryAndPagedGuides(helper,page.getStartIndex(),page.getPageSize()));
        page.setTotalRecNum((long)guideDao.getQueryGuideCount(helper));
        return page;
    }

    @Override
    public Integer getQueryGuideCount(GuideQueryHelper helper) {
        return guideDao.getQueryGuideCount(helper);
    }

    @Override
    public Guide getGuideById(Integer id) {
        return guideDao.getGuideById(id);
    }

    @Override
    public List<Guide> getGuideByAdminId(Integer id) {
        return guideDao.getGuideByAdminId(id);
    }

    @Override
    public List<Guide> getGuideByGuideTypeId(Integer id) {
        return guideDao.getGuideByGuideTypeId(id);
    }

    @Override
    public List<Guide> getGuideByGuideTypeIdU(Integer id) {
        return guideDao.getGuideByGuideTypeIdU(id);
    }

    @Override
    public List<Guide> getGuideByAttracId(Integer attracId) {
        return guideDao.getGuideByAttracId(attracId);
    }

    @Override
    public List<Guide> getGuideByAttracIdU(Integer attracId) {
        return guideDao.getGuideByAttracIdU(attracId);
    }

    @Override
    public List<Guide> getRandomGuide() {
        return guideDao.getRandomGuide();
    }

    @Override
    public List<Guide> getRandomGuideU() {
        return guideDao.getRandomGuideU();
    }

    @Override
    public String getGuidePicById(Integer id) {
        return guideDao.getGuidePicById(id);
    }

    @Override
    public void updateGuide(Guide guide) {

        GuideType oldGuideType=guideDao.getGuideById(guide.getGuideId()).getGuideType();

        guideDao.updateGuide(guide);

        oldGuideType.setGuideCount(oldGuideType.getGuideCount()-1);
        guideTypeDao.updateGuideCountData(oldGuideType);

        GuideType newGuideType=guideTypeDao.getGuideTypeById(guide.getGuideType().getGuideTypeId());
        newGuideType.setGuideCount(newGuideType.getGuideCount()+1);
        guideTypeDao.updateGuideCountData(newGuideType);
    }

    @Override
    public void updateGuideStatus(Guide guide) {
        guideDao.updateGuideStatus(guide);
    }

    @Override
    public void updateGuideClickCount(Guide guide) {
        guideDao.updateGuideClickCount(guide);
    }
}
