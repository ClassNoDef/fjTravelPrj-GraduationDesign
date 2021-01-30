package com.travel.fj.service;

import com.travel.fj.dao.AttractionTypeDao;
import com.travel.fj.domain.AttractionType;
import com.travel.fj.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionTypeServiceImpl implements AttractionTypeService {

    @Autowired
    AttractionTypeDao attractionTypeDao;
    @Override
    public void createAttracType(AttractionType attractionType) {

        try {
            attractionTypeDao.createAttracType(attractionType);
        }catch (Exception e){
                if(e.getMessage().contains("UNIQUE_NAME"))
                    throw  new DataAccessException("景点类型重复！");
            }
    }

    @Override
    public List<AttractionType> loadAllAttracType() {
        return attractionTypeDao.loadAllAttracType();
    }

    @Override
    public AttractionType getAttracTypeById(int id) {
        return attractionTypeDao.getAttracTypeById(id);
    }

    @Override
    public void updateAttracType(AttractionType attractionType) {

        try {
            attractionTypeDao.updateAttracType(attractionType);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME"))
                throw  new DataAccessException("景点类型重复！");
        }
    }

    @Override
    public void updateAttracCountData(AttractionType attractionType) {
        attractionTypeDao.updateAttracCountData(attractionType);
    }

    @Override
    public void deleteAttracType(int id) {
        try {
            attractionTypeDao.deleteAttracType(id);
        }catch (Exception e){
            if(e.getMessage().contains("FK_ATTRAC_TYPE"))
                throw  new DataAccessException("该类型下存在景点，不可删除！");
        }

    }
}
