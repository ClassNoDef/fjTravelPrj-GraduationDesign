package com.travel.fj.dao;

import com.travel.fj.domain.SysLog;

import java.util.List;

public interface SysLogDao {

    void createSyslog(SysLog sysLog);
    List<SysLog> loadAllSysLog();
}
