package com.travel.fj.service;

import com.travel.fj.domain.User;
import com.travel.fj.queryhelper.UserQueryHelper;
import com.travel.fj.utils.Page;

import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> loadAllUser();
    List<User> loadQueryUser( UserQueryHelper helper);
    Page loadQueryAndPageUser(UserQueryHelper helper, Page page);
    Integer getQueryUserCount(UserQueryHelper helper);
    User getUserById(int id);
    User getUserByName(String name);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    void updateUserPwd(User user);
    void updateUserInfo(User user);
    void updateUserData(User user);
    boolean loginCheck(User user);
    void updateUserStatus(User user);
    List<String> getNameList();
    List<String> getEmailList();
    List<String> getPhoneList();
    byte[] getUserPic(int id);
}
