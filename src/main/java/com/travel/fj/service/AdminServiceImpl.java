package com.travel.fj.service;

import com.travel.fj.dao.AdminDao;
import com.travel.fj.domain.Admin;
import com.travel.fj.exception.ApplicationException;
import com.travel.fj.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    @Override
    public void createAdmin(Admin admin) {
        if(admin.getAdminName().equals("Admin")||admin.getAdminName().equals("System"))
            throw new ApplicationException("不允许与超级管理员同名！");
        try {
            adminDao.createAdmin(admin);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME"))
                throw  new DataAccessException("管理员名不允许重复！");
        }
    }

    @Override
    public List<Admin> loadAllAdmin() {
        return adminDao.loadAllAdmin();
    }

    @Override
    public Admin getAdminById(int id) {
        return adminDao.getAdminById(id);
    }

    @Override
    public Admin getAdminByName(String name) {
        return adminDao.getAdminByName(name);
    }

    @Override
    public void updateAdminPwd(Admin admin) {
        adminDao.updateAdminPwd(admin);
    }

    @Override
    public void updateAdminName(Admin admin) {
        if(admin.getAdminName().equals("Admin")||admin.getAdminName().equals("System"))
            throw new ApplicationException("不允许与超级管理员同名！");
        try{
        adminDao.updateAdminName(admin);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME"))
                throw  new DataAccessException("管理员名不允许重复！");
        }

    }

    @Override
    public void updateAdminStatus(Admin admin) {

        adminDao.updateAdminStatus(admin);
    }

    @Override
    public boolean loginCheck(Admin admin) {
        Admin real;
        String n = admin.getAdminName();
        real =adminDao.getAdminByName(n);

        if(real==null)
            throw new DataAccessException("管理员-"+n+"不存在！");
        else if(real.getAdminStatus()==1)
            throw new DataAccessException("管理员-"+n+"已经被冻结！");
        else if(real.getAdminPwd().equals(admin.getAdminPwd()))
            return true;
        else
            throw new DataAccessException("密码错误！");
    }

}
