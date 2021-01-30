package com.travel.fj.service;

import com.travel.fj.dao.UserDao;
import com.travel.fj.domain.User;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.queryhelper.UserQueryHelper;
import com.travel.fj.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public void createUser(User user) {
        try {
            userDao.createUser(user);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME")){
                throw new DataAccessException("用户名已存在");
            }
            if(e.getMessage().contains("UNIQUE_EMAIL")){
                throw new DataAccessException("邮箱已经被注册");
            }
            if(e.getMessage().contains("UNIQUE_PHONE")){
                throw new DataAccessException("手机号已被注册");
            }
        }
    }

    @Override
    public List<User> loadAllUser() {
        return userDao.loadAllUser();
    }

    @Override
    public List<User> loadQueryUser(UserQueryHelper helper) {
        return userDao.loadQueryUser(helper);
    }

    @Override
    public Page loadQueryAndPageUser(UserQueryHelper helper, Page page) {
        page.setPageContent(userDao.loadQueryAndPageUser(helper,page.getStartIndex(),page.getPageSize()));

        page.setTotalRecNum((long)userDao.getQueryUserCount(helper));
        return page;
    }

    @Override
    public Integer getQueryUserCount(UserQueryHelper helper) {
        return userDao.getQueryUserCount(helper);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    @Override
    public void updateUserPwd(User user) {
        userDao.updateUserPwd(user);
    }

    @Override
    public void updateUserInfo(User user) {
        try{
        userDao.updateUserInfo(user);
        }catch (Exception e){
            if(e.getMessage().contains("UNIQUE_NAME")){
                throw new DataAccessException("用户名已存在");
            }
            if(e.getMessage().contains("UNIQUE_EMAIL")){
                throw new DataAccessException("邮箱已经被注册");
            }
            if(e.getMessage().contains("UNIQUE_PHONE")){
                throw new DataAccessException("手机号已被注册");
            }
        }

    }

    @Override
    public void updateUserData(User user) {
        userDao.updateUserData(user);
    }

    @Override
    public boolean loginCheck(User user) {
            User real=null;
            if(user.getUserName()!=null) {
                //获取登录用户名
                String n = user.getUserName();

                //取得正确的用户数据
                real = userDao.getUserByName(n);
            }
            if(user.getUserEmail()!=null) {
            String e = user.getUserEmail();
             real = userDao.getUserByEmail(e);
            }
            if(user.getUserPhone()!=null) {
                String p = user.getUserPhone();
                real = userDao.getUserByPhone(p);
            }

            //根据具体错误抛出异常
            if(real==null) {
                throw new DataAccessException("用户不存在！");
            }
            else if(real.getUserStatus()==1){
                throw new DataAccessException("该账户已经被冻结，请联系客服！");
            }
            else if(real.getUserStatus()==2){
                throw new DataAccessException("该账户未核验，无法登录");
            }
            else if(real.getUserPwd().equals(user.getUserPwd()))
                return true;
            else
                throw new DataAccessException("密码错误！");


    }

    @Override
    public void updateUserStatus(User user) {
        userDao.updateUserStatus(user);
    }

    @Override
    public List<String> getNameList() {
        return userDao.getNameList();
    }

    @Override
    public List<String> getEmailList() {
        return userDao.getEmailList();
    }

    @Override
    public List<String> getPhoneList() {
        return userDao.getPhoneList();
    }

    @Override
    public byte[] getUserPic(int id) {
        if(userDao.getUserPic(id)!=null){
            return  (byte[])userDao.getUserPic(id).get("imgBytes");
        }
        return null;
    }

}
