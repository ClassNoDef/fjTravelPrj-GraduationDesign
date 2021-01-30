package com.travel.fj.dao;

import com.travel.fj.domain.AttractionType;

import java.util.List;

public interface AttractionTypeDao {

    void createAttracType(AttractionType attractionType);
    List<AttractionType>loadAllAttracType();
    AttractionType getAttracTypeById(int id);
    void updateAttracType(AttractionType attractionType);
    void updateAttracCountData(AttractionType attractionType);
    void deleteAttracType(int id);
}
