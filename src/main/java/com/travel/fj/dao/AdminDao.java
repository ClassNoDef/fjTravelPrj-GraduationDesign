package com.travel.fj.dao;

import com.travel.fj.domain.Admin;

import java.util.List;

public interface AdminDao {
    void createAdmin(Admin admin);
    List<Admin> loadAllAdmin();
    Admin getAdminById(int id);
    Admin getAdminByName(String name);
    void updateAdminPwd(Admin admin);
    void updateAdminName(Admin admin);
    void updateAdminStatus(Admin admin);
}
