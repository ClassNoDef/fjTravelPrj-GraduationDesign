package com.travel.fj.queryhelper;

public class GuideQueryHelper {

    private String queryGuideTitle;
    private Integer queryGuideAuthorId;
    private Integer queryGuideType;
    private Integer queryGuideAttracId;
    private Integer queryGuideStatus;
    private Integer currentPageNum;

    public GuideQueryHelper() {
        this.queryGuideTitle="";
    }

    public String getQueryGuideTitle() {
        return queryGuideTitle;
    }

    public void setQueryGuideTitle(String queryGuideTitle) {
        this.queryGuideTitle = queryGuideTitle;
    }

    public Integer getQueryGuideAuthorId() {
        return queryGuideAuthorId;
    }

    public void setQueryGuideAuthorId(Integer queryGuideAuthorId) {
        this.queryGuideAuthorId = queryGuideAuthorId;
    }

    public Integer getQueryGuideType() {
        return queryGuideType;
    }

    public void setQueryGuideType(Integer queryGuideType) {
        this.queryGuideType = queryGuideType;
    }

    public Integer getQueryGuideAttracId() {
        return queryGuideAttracId;
    }

    public void setQueryGuideAttracId(Integer queryGuideAttracId) {
        this.queryGuideAttracId = queryGuideAttracId;
    }

    public Integer getQueryGuideStatus() {
        return queryGuideStatus;
    }

    public void setQueryGuideStatus(Integer queryGuideStatus) {
        this.queryGuideStatus = queryGuideStatus;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
