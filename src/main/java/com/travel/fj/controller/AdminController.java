package com.travel.fj.controller;

import com.travel.fj.domain.Admin;
import com.travel.fj.domain.SysLog;
import com.travel.fj.domain.User;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.queryhelper.UserQueryHelper;
import com.travel.fj.service.AdminService;
import com.travel.fj.service.SysLogService;
import com.travel.fj.service.UserService;
import com.travel.fj.service.WorkService;
import com.travel.fj.utils.GenString;
import com.travel.fj.utils.MakeVerificationCodeImage;
import com.travel.fj.utils.Page;
import com.travel.fj.utils.ResultInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @Autowired
    WorkService workService;

    @Autowired
    SysLogService sysLogService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession httpSession;

    private static final Logger logger= LogManager.getLogger(AdminController.class);

    @GetMapping("login")
    public String toLoginPage(Model model ,HttpSession session) {
        if (session.getAttribute("logonAdmin")!=null)
            return "redirect:/admin/adminCenter";
        //创建一个用于绑定数据的空对象放入模型
        model.addAttribute("admin",new Admin());
        //返回登录页面，让管理员登录
        return "admin_page/admin_login_page";
    }

    @PostMapping("login")
    public String login(Admin admin, Model model, HttpSession session){
        //对用户登录密码进行MD5加密
        String encodePwd= DigestUtils.md5DigestAsHex(admin.getAdminPwd().getBytes());
        //将加密后的密码放进用户变量
        admin.setAdminPwd(encodePwd);
       // String subVeriCode=(String) session.getAttribute("securityCode");
        try {
            //登录验证
            adminService.loginCheck(admin);
            //已经用Ajax代替
           // if(subVeriCode.equals(s))

        }catch (DataAccessException e){
            //向模型中回填错误信息，并返回登录界面，相关字段显示错误信息
            model.addAttribute("errmsg",e.getMessage());
            System.out.println(e.getMessage());
            sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"管理员"+admin.getAdminName()+"登录失败:"+e.getMessage(),null));
            //登录错误返回登录页面
           return "admin_page/admin_login_page";
        }
        //登录成功将信息放入session
        session.setAttribute("logonAdmin",admin);
        sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"管理员"+admin.getAdminName()+"登录成功",admin));
        //return  new ResultInfo(loginResult,loginResult?"登录成功":"登录失败",null);
        return "redirect:/admin/adminCenter";
    }

    @RequestMapping("index")
    public  String index(){
        return "admin_page/index";
    }

    @RequestMapping("adminCenter")
    public String adminCenter(Model model,HttpSession session) {
        //读取session中的用户信息
        Admin logonAdmin = (Admin) session.getAttribute("logonAdmin");
        //System.out.println(adminService.getAdminByName(logonAdmin.getAdminName()));
        //向模型中添加用户信息
        model.addAttribute("admin", adminService.getAdminByName(logonAdmin.getAdminName()));
        //返回用户中心界面
        return "admin_page/admin_center_page";
    }

    @RequestMapping("logout")
    public String logOut(HttpSession session){
        if(session!=null){
            Admin logon=(Admin)session.getAttribute("logonAdmin");
            //session.invalidate(); 此方法会摧毁整个session，导致在管理员或用户一方登出时另一方同时登出
            session.removeAttribute("logonAdmin");
            sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"管理员"+logon.getAdminName()+"登出:",null));
        }
        return "redirect:/admin/login";
    }
    @RequestMapping("userManage")
    public String  userManage(Model model){
        model.addAttribute("loadedUser",userService.loadAllUser());
        return "admin_page/admin_user_manage_page";
    }
    @RequestMapping("freezeUser/{userId}")
    @ResponseBody
    public Map freezeUser(@PathVariable String userId){
        User user =new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserStatus(1);
        userService.updateUserStatus(user);
        workService.blockFreezeUserWork(Integer.parseInt(userId));
        sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"冻结用户，ID"+userId,getLogonAdmin()));
        Map<String,String> result=new HashMap();
        result.put("code","T");
        result.put("msg","冻结成功");
        return result;
    }

    @RequestMapping("activeUserByAdmin/{userId}")
    @ResponseBody
    public Map activeUserByAdmin(@PathVariable String userId){
        User user =new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserStatus(0);
        userService.updateUserStatus(user);
        Map<String,String> result=new HashMap();
        result.put("code","T");
        result.put("msg","开通成功");
        return result;
    }

    @RequestMapping("unFreezeUser/{userId}")
    @ResponseBody
    public Map unFreezeUser(@PathVariable String userId){
        User user =new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserStatus(0);
        userService.updateUserStatus(user);
        workService.unBlockFreezeUserWork(Integer.parseInt(userId));
        sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"解冻用户，ID"+userId,getLogonAdmin()));
        Map<String,String> result=new HashMap();
        result.put("code","T");
        result.put("msg","解冻成功");
        return result;
    }
    @GetMapping("createAdmin")
    public  String toCreateAdminPage(Model model,HttpSession session){


        if(isSuperAdminCheck(session)) {
            //创建一个用于绑定数据的空对象放入模型
            model.addAttribute("admin", new Admin());

            return "admin_page/admin_create_new_admin_page";
        }

        else {
            logger.error("权限错误，非超级管理员无法新建管理员！");
            return "redirect:/admin/adminCenter";
        }
    }

    @PostMapping("createAdmin" )
    public String register(Admin admin, Model model, HttpSession session){
        //对管理员注册密码进行MD5加密
        String encodePwd= DigestUtils.md5DigestAsHex(admin.getAdminPwd().getBytes());
        //将加密后的密码放进管理员变量
        admin.setAdminPwd(encodePwd);
        try {
            //创建用户
            adminService.createAdmin(admin);
       }catch (RuntimeException e){
            //向模型中回填错误信息，并返回登录界面，相关字段显示错误信息
            model.addAttribute("errmsg",e.getMessage());
            System.out.println(e.getMessage());
            //返回注册界面
            return "admin_page/admin_create_new_admin_page";
        }
        sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"创建管理员成功，管理员名："+admin.getAdminName(),getLogonAdmin()));
        //重定向至注册成功界面，重定向：防止表单重复提交
        return "redirect:/admin/adminManage";
    }
    @RequestMapping("adminManage")
    public String  adminManage(Model model,HttpSession session){
        if(isSuperAdminCheck(session)) {
            model.addAttribute("loadedAdmin", adminService.loadAllAdmin());
            return "admin_page/admin_admin_manage_page";
        }
        else {
            logger.error("权限错误，非超级管理员不允许管理管理员信息！");
            return "redirect:/admin/adminCenter";
        }
    }

    @PostMapping("loadAllUserAjax")
    @ResponseBody
    public Page loadAllUserAjax(UserQueryHelper uqData){
        if("".equals(uqData.getQueryUserSex())){
            uqData.setQueryUserSex(null);
        }
        if("".equals(uqData.getQueryUserName())){
            uqData.setQueryUserName(null);
        }
        Page p=new Page();
        p.setPageNo(uqData.getCurrentPageNum());
        Page r=userService.loadQueryAndPageUser(uqData,p);
        return r;
    }

    @RequestMapping("freezeAdmin/{adminId}")
    public String freezeAdmin(@PathVariable String adminId,HttpSession session){
        int id= Integer.parseInt(adminId);
        if(id==1||id==2){
            logger.error("不允许的操作，超级管理员状态不允许被更改！");
            return "redirect:/admin/adminCenter";
        }

        else if(isSuperAdminCheck(session)) {
            Admin a=new Admin();
            a.setAdminId(id);
            a.setAdminStatus(1);
            adminService.updateAdminStatus(a);
            sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"冻结管理员，ID"+adminId,getLogonAdmin()));
            return "redirect:/admin/adminManage";

        }
        else {
            logger.error("权限错误，非超级管理员无法更改用户状态！");
            return "redirect:/admin/adminCenter";
        }

    }


    @RequestMapping("unFreezeAdmin/{adminId}")
    public String unFreezeAdmin(@PathVariable String adminId,HttpSession session){

        int id= Integer.parseInt(adminId);
        if(id==1||id==2){
            logger.error("不允许的操作，超级管理员状态不允许被更改！");
            return "redirect:/admin/adminCenter";
        }
        else if(isSuperAdminCheck(session)) {
            Admin a=new Admin();
            a.setAdminId(id);
            a.setAdminStatus(0);
            adminService.updateAdminStatus(a);
            sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"解冻管理员，ID"+adminId,getLogonAdmin()));
            return "redirect:/admin/adminManage";
        }
        else {
            logger.error("权限错误，非超级管理员无法更改用户状态！");
            return "redirect:/admin/adminCenter";
        }
    }

    private  boolean isSuperAdminCheck(HttpSession session){
        Admin admin =(Admin) session.getAttribute("logonAdmin");
        return (admin.getAdminName().equals("Admin")||admin.getAdminName().equals("System"));
    }





    @GetMapping("updateName")
    public String toUpdate(Model model,HttpSession session){
        Admin preUpdateAdmin=(Admin) session.getAttribute("logonAdmin");
        model.addAttribute("preUpdateAdmin",adminService.getAdminByName(preUpdateAdmin.getAdminName()));
        return "admin_page/admin_update_name_page";
    }

    @PostMapping("updateName")
    public String updateName(Admin admin,Model model,HttpSession session){
        try {
            adminService.updateAdminName(admin);
        }catch (RuntimeException e){
            model.addAttribute("errmsg",e.getMessage());
            Admin preUpdateAdmin=(Admin) session.getAttribute("logonAdmin");
            model.addAttribute("preUpdateAdmin",adminService.getAdminByName(preUpdateAdmin.getAdminName()));
            return "admin_page/admin_update_name_page";
        }
        sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"更新用户名，新名称"+admin.getAdminName(),getLogonAdmin()));
        //更新session里的信息
        Admin updateSession=new Admin();
        String pwd=adminService.getAdminByName(admin.getAdminName()).getAdminPwd();
        updateSession.setAdminName(admin.getAdminName());
        updateSession.setAdminPwd(pwd);
        session.setAttribute("logonAdmin",updateSession);
        return "redirect:/admin/changeAdminNameSucceed";
    }

    @RequestMapping("changeAdminNameSucceed")
    public String changeAdminNameSucceed(){

        return "admin_page/admin_change_info_success_page";
    }

    @PostMapping("checkCurrentPwd")
    @ResponseBody
    public String checkCurrentPwd(String aid,String pwd){

        int id;
        try{
            id=Integer.parseInt(aid);
        }catch (NumberFormatException e){
            return "false";
        }
        Admin a=adminService.getAdminById(id);
        if(a==null) {
            return "false";
        }
        else{
            String currentPwd = a.getAdminPwd();
            if(currentPwd.equals(pwd)){
                return "true";
            }
            else
                return "false";
        }
    }

    @GetMapping("updatePwd")
    public String toUpdatePwd(Model model,HttpSession session){
        Admin preUpdateAdmin=(Admin) session.getAttribute("logonAdmin");
        model.addAttribute("preUpdateAdmin",adminService.getAdminByName(preUpdateAdmin.getAdminName()));
        return "admin_page/admin_update_pwd_page";
        }

        @PostMapping("updatePwd")
        public String updatePwd(Admin admin){
            String encodePwd= DigestUtils.md5DigestAsHex(admin.getAdminPwd().getBytes());
            admin.setAdminPwd(encodePwd);
            adminService.updateAdminPwd(admin);
            sysLogService.createSyslog(new SysLog(request.getRemoteAddr(),"更新密码，",getLogonAdmin()));
            return "redirect:/admin/changePwdSucceed";

        }

        @RequestMapping("changePwdSucceed")
        public  String changePwdSucceed(HttpSession session){
                if(session!=null){
                    session.removeAttribute("logonAdmin");
                }
                return "admin_page/admin_change_pwd_success_page";
        }



    @RequestMapping(value="vericode",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getVeriCode(HttpSession session){
        String securityCode = GenString.makeString(5, false);
        ByteArrayInputStream imageStream = MakeVerificationCodeImage.getImageAsInputStream(securityCode);
        byte[] img=new byte[imageStream.available()];
        session.setAttribute("securityCode", securityCode);
        imageStream.read(img,0,imageStream.available());
        return  img;
    }
    @RequestMapping(value="vericodeCheck",produces="application/json")
    @ResponseBody
    public ResultInfo veriCodeCheck(String subVeriCode,HttpSession session){
        String realVreiCode=(String) session.getAttribute("securityCode");
        if(!realVreiCode.equals(subVeriCode))
            return  new ResultInfo(false,"验证码错误",null);
        else
            return  new ResultInfo(true,"验证码正确",null);
    }
    @RequestMapping("test")
    public String test(Model model ,HttpSession session){
        //读取session中的用户信息
        Admin logonAdmin = (Admin) session.getAttribute("logonAdmin");
        System.out.println(adminService.getAdminByName(logonAdmin.getAdminName()));
        //向模型中添加用户信息
        model.addAttribute("admin", adminService.getAdminByName(logonAdmin.getAdminName()));
        //返回用户中心界面
        return "admin_page/center";
    }

    @RequestMapping("requireLogin")
    public String requireLogin(HttpSession session){
        if (session.getAttribute("logonAdmin")!=null)
            return "redirect:/admin/adminCenter";
        else
            return "error_page/admin_no_login_error_page";
    }



    private Admin getLogonAdmin(){
        return (Admin) httpSession.getAttribute("logonAdmin");
    }
}
