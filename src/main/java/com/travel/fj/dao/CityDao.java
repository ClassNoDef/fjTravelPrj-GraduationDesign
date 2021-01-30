package com.travel.fj.dao;

import com.travel.fj.domain.City;

import java.util.List;
import java.util.Map;

public interface CityDao {
    void createCity(City city);
    List<City> loadAllCity();
    City getCityById(Integer id);
    City getCityByName(String name);
    void updateCity(City city);
    void updateAttracCountData(City city);
    void deleteCity(Integer id);
    Map getCityPic(Integer id);
}
