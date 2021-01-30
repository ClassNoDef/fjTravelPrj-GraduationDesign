package com.travel.fj.domain;

public class Admin extends  ValueObject {

    private  int adminId;
    private String adminName;
    private String adminPwd;
    private int isSuper;
    private int adminStatus;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public int getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(int isSuper) {
        this.isSuper = isSuper;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int admininStatus) {
        this.adminStatus = admininStatus;
    }
}
