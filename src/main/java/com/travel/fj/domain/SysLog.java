package com.travel.fj.domain;

import java.util.Date;

public class SysLog extends ValueObject {

    private Integer logId;
    private Date logTime;
    private String operIP;
    private String operAction;
    private Admin operAdmin;

    public SysLog(String operIP, String operAction, Admin operAdmin) {
        this.operIP = operIP;
        this.operAction = operAction;
        this.operAdmin = operAdmin;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getOperIP() {
        return operIP;
    }

    public void setOperIP(String operIP) {
        this.operIP = operIP;
    }

    public String getOperAction() {
        return operAction;
    }

    public void setOperAction(String operAction) {
        this.operAction = operAction;
    }

    public Admin getOperAdmin() {
        return operAdmin;
    }

    public void setOperAdmin(Admin operAdmin) {
        this.operAdmin = operAdmin;
    }
}
