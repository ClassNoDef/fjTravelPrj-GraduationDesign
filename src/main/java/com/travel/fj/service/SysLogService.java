package com.travel.fj.service;

import com.travel.fj.domain.SysLog;

import java.util.List;

public interface SysLogService {

    void createSyslog(SysLog sysLog);
    List<SysLog> loadAllSysLog();
}
