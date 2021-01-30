package com.travel.fj.service;

import com.travel.fj.dao.GuideTypeDao;
import com.travel.fj.domain.GuideType;
import com.travel.fj.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideTypeServiceImpl implements GuideTypeService{

    @Autowired
    GuideTypeDao guideTypeDao;

    @Override
    public void createGuideType(GuideType guideType) {
        try{
        guideTypeDao.createGuideType(guideType);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_GUIDE_TYPE_NAME"))
                throw  new DataAccessException("指南类型重复！");
        }
    }

    @Override
    public List<GuideType> loadAllGuideType() {
        return guideTypeDao.loadAllGuideType();
    }

    @Override
    public GuideType getGuideTypeById(int id) {
        GuideType result=guideTypeDao.getGuideTypeById(id);
        if(result==null){
            throw new DataAccessException("没有找到ID为"+id+"的指南类型");
        }
        return result;
    }

    @Override
    public void updateGuideType(GuideType guideType) {

        try{
            guideTypeDao.updateGuideType(guideType);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_GUIDE_TYPE_NAME"))
                throw  new DataAccessException("指南类型重复！");
        }
    }

    @Override
    public void deleteGuideType(int id) {
        try {
            guideTypeDao.deleteGuideType(id);
        }catch (RuntimeException e){
            if(e.getMessage().contains("tbl_guide_tbl_guide_type_fk")){
                throw  new DataAccessException("该类型下存在指南，不可删除！");
            }
        }
    }

    @Override
    public void updateGuideCountData(GuideType guideType) {
        guideTypeDao.updateGuideCountData(guideType);
    }
}
