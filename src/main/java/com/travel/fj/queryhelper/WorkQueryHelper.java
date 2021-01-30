package com.travel.fj.queryhelper;

public class WorkQueryHelper {

    private String queryWorkTitle;
    private Integer queryWorkAuthorId;
    private Integer queryWorkType;
    private Integer queryWorkAttracId;
    private Integer queryWorkStatus;
    private Integer currentPageNum;

    public WorkQueryHelper() {
        this.queryWorkTitle="";
    }

    public String getQueryWorkTitle() {
        return queryWorkTitle;
    }

    public void setQueryWorkTitle(String queryWorkTitle) {
        this.queryWorkTitle = queryWorkTitle;
    }

    public Integer getQueryWorkAuthorId() {
        return queryWorkAuthorId;
    }

    public void setQueryWorkAuthorId(Integer queryWorkAuthorId) {
        this.queryWorkAuthorId = queryWorkAuthorId;
    }

    public Integer getQueryWorkType() {
        return queryWorkType;
    }

    public void setQueryWorkType(Integer queryWorkType) {
        this.queryWorkType = queryWorkType;
    }

    public Integer getQueryWorkAttracId() {
        return queryWorkAttracId;
    }

    public void setQueryWorkAttracId(Integer queryWorkAttracId) {
        this.queryWorkAttracId = queryWorkAttracId;
    }

    public Integer getQueryWorkStatus() {
        return queryWorkStatus;
    }

    public void setQueryWorkStatus(Integer queryWorkStatus) {
        this.queryWorkStatus = queryWorkStatus;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
