package com.travel.fj.dao;

import com.travel.fj.domain.User;
import com.travel.fj.queryhelper.UserQueryHelper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {
    void createUser(User user);
    List<User> loadAllUser();
    List<User> loadQueryUser(@Param("helper")UserQueryHelper helper);
    List<User> loadQueryAndPageUser
            (@Param("helper")UserQueryHelper helper,@Param("startIndex") Integer startIndex,@Param("fetchSize") Integer fetchSize);
    Integer getQueryUserCount(@Param("helper")UserQueryHelper helper);
    User getUserById(int id);
    User getUserById1(int id);
    User getUserByName(String name);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    void updateUserPwd(User user);
    void updateUserInfo(User user);
    void updateUserData(User user);
    void updateUserStatus(User user);
    List<String> getNameList();
    List<String> getEmailList();
    List<String> getPhoneList();
    Map getUserPic(int id);
}
