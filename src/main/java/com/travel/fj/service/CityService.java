package com.travel.fj.service;

import com.travel.fj.domain.City;

import java.util.List;

public interface CityService {
    void createCity(City city);
    List<City> loadAllCity();
    City getCityById(Integer id);
    City getCityByName(String name);
    void updateCity(City city);
    void updateAttracCountData(City city);
    void deleteCity(Integer id);
    byte[] getCityPic(Integer id);
}
