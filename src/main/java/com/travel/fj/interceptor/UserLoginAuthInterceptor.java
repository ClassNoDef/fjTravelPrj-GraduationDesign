package com.travel.fj.interceptor;


import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class UserLoginAuthInterceptor implements HandlerInterceptor {


     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

         HttpSession session= request.getSession();
         if(session.getAttribute("logonUser")!=null){
                return true;
         }

         else{
             PrintWriter printWriter = response.getWriter();
             printWriter.write("您未登录，请登录");
             response.sendRedirect("/user/requireLogin");
             return false;
         }
     }


     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {

    }


     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
    }

}
