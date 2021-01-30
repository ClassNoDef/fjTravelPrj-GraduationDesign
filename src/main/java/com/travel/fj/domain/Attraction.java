package com.travel.fj.domain;

public class Attraction extends ValueObject{
    private Integer attracId;
    private String attracName;
    private  City attracCity;
    private String attracAdd;
    private AttractionType attracType;
    private Integer attracNoteCount;
    private Integer attracGuideCount;
    private String attracTips;
    private String attracPicFileList;
    private Integer attracClickCount;
    private Integer isBlocked;

    public Integer getAttracId() {
        return attracId;
    }

    public void setAttracId(Integer attracId) {
        this.attracId = attracId;
    }

    public String getAttracName() {
        return attracName;
    }

    public void setAttracName(String attracName) {
        this.attracName = attracName;
    }


    public String getAttracAdd() {
        return attracAdd;
    }

    public void setAttracAdd(String attracAdd) {
        this.attracAdd = attracAdd;
    }


    public Integer getAttracNoteCount() {
        return attracNoteCount;
    }

    public void setAttracNoteCount(Integer attracNoteCount) {
        this.attracNoteCount = attracNoteCount;
    }

    public Integer getAttracGuideCount() {
        return attracGuideCount;
    }

    public void setAttracGuideCount(Integer attracGuideCount) {
        this.attracGuideCount = attracGuideCount;
    }

    public String getAttracPicFileList() {
        return attracPicFileList;
    }

    public void setAttracPicFileList(String attracPicFileList) {
        this.attracPicFileList = attracPicFileList;
    }

    public String getAttracTips() {
        return attracTips;
    }

    public void setAttracTips(String attracTips) {
        this.attracTips = attracTips;
    }



    public AttractionType getAttracType() {
        return attracType;
    }

    public void setAttracType(AttractionType attractionType) {
        this.attracType = attractionType;
    }

    public City getAttracCity() {
        return attracCity;
    }

    public void setAttracCity(City attracCity) {
        this.attracCity = attracCity;
    }

    public Integer getAttracClickCount() {
        return attracClickCount;
    }

    public void setAttracClickCount(Integer attracClickCount) {
        this.attracClickCount = attracClickCount;
    }

    public Integer getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Integer isBlocked) {
        this.isBlocked = isBlocked;
    }
}
