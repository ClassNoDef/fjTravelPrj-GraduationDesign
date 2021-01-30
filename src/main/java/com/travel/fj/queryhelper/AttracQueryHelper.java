package com.travel.fj.queryhelper;

import com.travel.fj.domain.ValueObject;

public class AttracQueryHelper extends ValueObject {

    private String queryAttracName;
    private Integer queryAttracCity;
    private String queryAttracAdd;
    private Integer queryAttracType;
    private Integer currentPageNum;

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public AttracQueryHelper() {
        this.queryAttracAdd="";
        this.queryAttracName="";
    }

    public String getQueryAttracName() {
        return queryAttracName;
    }

    public void setQueryAttracName(String queryAttracName) {
        this.queryAttracName = queryAttracName;
    }

    public Integer getQueryAttracCity() {
        return queryAttracCity;
    }

    public void setQueryAttracCity(Integer queryAttracCity) {
        this.queryAttracCity = queryAttracCity;
    }

    public String getQueryAttracAdd() {
        return queryAttracAdd;
    }

    public void setQueryAttracAdd(String queryAttracAdd) {
        this.queryAttracAdd = queryAttracAdd;
    }

    public Integer getQueryAttracType() {
        return queryAttracType;
    }

    public void setQueryAttracType(Integer queryAttracType) {
        this.queryAttracType = queryAttracType;
    }
}
