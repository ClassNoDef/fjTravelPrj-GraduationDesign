package com.travel.fj.queryhelper;

public class UserQueryHelper {

    private String queryUserName;
    private String queryUserSex;
    private Integer queryUserStatus;
    private Integer currentPageNum;

    public UserQueryHelper() {
        this.queryUserName="";
        this.queryUserSex="";
    }

    public String getQueryUserName() {
        return queryUserName;
    }

    public void setQueryUserName(String queryUserName) {
        this.queryUserName = queryUserName;
    }

    public String getQueryUserSex() {
        return queryUserSex;
    }

    public void setQueryUserSex(String queryUserSex) {
        this.queryUserSex = queryUserSex;
    }

    public Integer getQueryUserStatus() {
        return queryUserStatus;
    }

    public void setQueryUserStatus(Integer queryUserStatus) {
        this.queryUserStatus = queryUserStatus;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }
}
