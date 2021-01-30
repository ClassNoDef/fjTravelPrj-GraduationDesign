package com.travel.fj.config;

import com.travel.fj.interceptor.AdminLoginAuthInterceptor;
import com.travel.fj.interceptor.UserLoginAuthInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MyWebMvcConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginAuthInterceptor())
                .addPathPatterns("/user/**","/work/**").excludePathPatterns("/user/toLogin",
                "/user/vericode","/user/vericodeCheck","/user/vericodeCheckJQ","/user/toRegister","/user/findUserByEmail","/user/forgotPwd","/user/checkUser","/user/resetPwd","/user/activeUser","/user/register",
                "/user/registerSuccess","/user/requireLogin","/user/checkCurrentPwd","/user/checkDuplicateUserName","/user/checkDuplicateUserEmail","/user/checkDuplicateUserPhone","/user/getUserPic/**","/work/workDetail/**","/work/getWorkSinglePic/**","/work/getWorkPic/**",
                "/work/randomWorkAjax","/work/showWork/**","/work/searchWork/**","/libs/**","/js/**","/user/login","/img/**");
        /*静态文件都是放在static文件夹下面的  但是我们去访问这些静态资源的时候 在url中是
        不会通过/static请求的  而是直接通过/css
         或者是  /js   访问 因此我们需要做的就是将这些拦截排除掉  和/static是没有关系的*/
        registry.addInterceptor(new AdminLoginAuthInterceptor())
                .addPathPatterns("/admin/**","/attracMgr/**","/attracTypeMgr/**","/guideTypeMgr/**","/noteTypeMgr/**","/workMgr/**","/guideMgr/**","/cityMgr/**").excludePathPatterns("/admin/login",
                "/admin/login2","/admin/vericode","/admin/vericodeCheck","/admin/requireLogin","/admin/logout","/libs/**","/js/**,/css/**","/img/**");

    }



}
