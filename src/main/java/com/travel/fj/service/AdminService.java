package com.travel.fj.service;

import com.travel.fj.domain.Admin;

import java.util.List;

public interface AdminService {
    void createAdmin(Admin admin);
    List<Admin> loadAllAdmin();
    Admin getAdminById(int id);
    Admin getAdminByName(String name);
    void updateAdminPwd(Admin admin);
    void updateAdminName(Admin admin);
    void updateAdminStatus(Admin admin);
    boolean loginCheck(Admin admin);

}
