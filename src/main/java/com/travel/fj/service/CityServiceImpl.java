package com.travel.fj.service;

import com.travel.fj.dao.CityDao;
import com.travel.fj.domain.City;
import com.travel.fj.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityDao cityDao;

    @Override
    public void createCity(City city) {
        try{
        cityDao.createCity(city);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME"))
                throw  new DataAccessException("设区市重复！");
        }
    }

    @Override
    public List<City> loadAllCity() {
        List<City> result=cityDao.loadAllCity();
        if(result==null){
            throw new DataAccessException("没有查询到设区市，请检查数据库");
        }
        return result;
    }

    @Override
    public City getCityById(Integer id) {
        City result=cityDao.getCityById(id);
        return result;
    }

    @Override
    public City getCityByName(String name) {
        City result=cityDao.getCityByName(name);
        return result;
    }

    @Override
    public void updateCity(City city) {
        try{
            cityDao.updateCity(city);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME"))
                throw  new DataAccessException("设区市重复！");
        }
    }

    @Override
    public void updateAttracCountData(City city) {
        cityDao.updateAttracCountData(city);
    }

    @Override
    public void deleteCity(Integer id) {
        try {
            cityDao.deleteCity(id);
        }catch (Exception e){
            if(e.getMessage().contains("FK_ATTRAC_CITY"))
                throw  new DataAccessException("该设区市下有相关景点，无法删除！");
        }
    }

    @Override
    public byte[] getCityPic(Integer id) {
        if(cityDao.getCityPic(id)!=null){
            return  (byte[])cityDao.getCityPic(id).get("imgBytes");
        }
        return null;
    }
}
