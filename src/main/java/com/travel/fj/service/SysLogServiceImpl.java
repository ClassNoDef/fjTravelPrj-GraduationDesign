package com.travel.fj.service;

import com.travel.fj.dao.SysLogDao;
import com.travel.fj.domain.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    SysLogDao sysLogDao;

    @Override
    public void createSyslog(SysLog sysLog) {
        sysLogDao.createSyslog(sysLog);
    }

    @Override
    public List<SysLog> loadAllSysLog() {
        return sysLogDao.loadAllSysLog();
    }
}
