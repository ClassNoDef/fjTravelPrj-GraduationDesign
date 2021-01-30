package com.travel.fj.controller;

import com.travel.fj.domain.LikeObject;
import com.travel.fj.domain.User;
import com.travel.fj.domain.Work;
import com.travel.fj.exception.DataAccessException;
import com.travel.fj.service.LikeObjectService;
import com.travel.fj.service.UserService;
import com.travel.fj.service.WorkService;
import com.travel.fj.utils.GenString;
import com.travel.fj.utils.MakeVerificationCodeImage;
import com.travel.fj.utils.ResultInfo;

import com.travel.fj.utils.SendEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*checked*/
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

        @Autowired
        UserService userService;

        @Autowired
        LikeObjectService likeObjectService;

        @Autowired
        WorkService workService;

        @Autowired
        StringEncryptor stringEncryptor;

        @Autowired
        SendEmail sendEmail;

    private static final Logger logger= LogManager.getLogger(UserController.class);

        //用户中心首页
        @RequestMapping("index")
        public String index(){
            return "user_page/index";
        }

        //登录界面
        @GetMapping("login")
        public String toLoginPage(Model model, HttpSession session, HttpServletRequest request) {

            //已经登录则不显示登录界面
            if(session.getAttribute("logonUser")!=null){
                return "redirect:/";
            }
            //创建一个用于绑定数据的空对象放入模型
            model.addAttribute("user",new User());
            session.setAttribute("beforePage",request.getHeader("Referer"));
            //返回登录页面，让用户登录
            return "user_page/user_login_page";
        }

        //登录处理
        @PostMapping(value = "login" )
        public String login(User user, Model model, HttpSession session,String loginType){
            //对用户登录密码进行MD5加密
            String encodePwd= DigestUtils.md5DigestAsHex(user.getUserPwd().getBytes());
            //将加密后的密码放进用户变量
            user.setUserPwd(encodePwd);
            //已经在登录页面使用Ajax进行验证码验证，验证码错误无法提交表单登录，因此不再使用此句
            // String subVeriCode=(String) session.getAttribute("securityCode");
            // if(subVeriCode.equals(s))
            try {
                //登录验证
                userService.loginCheck(user);
            }catch (DataAccessException e){
                //向模型中回填错误信息，并返回登录界面，相关字段显示错误信息
                model.addAttribute("errmsg",e.getMessage());
                //登录错误返回登录页面
                return "user_page/user_login_page";
            }

            //从后台提取用户ID,使取用户头像变得方便
            if("n".equals(loginType)) {
                Integer id = userService.getUserByName(user.getUserName()).getUserId();
                user.setUserId(id);
            }
            else if("e".equals(loginType)) {
                String n = userService.getUserByEmail(user.getUserEmail()).getUserName();
                user.setUserName(n);
                Integer i = userService.getUserByEmail(user.getUserEmail()).getUserId();
                user.setUserId(i);
            }
            else if("p".equals(loginType)) {
                String p = userService.getUserByPhone(user.getUserPhone()).getUserName();
                user.setUserName(p);
                Integer i = userService.getUserByPhone(user.getUserPhone()).getUserId();
                user.setUserId(i);
            }
            //登录成功将信息放入session
            //session中的用户变量不完整，仅有Id,name,密码
            session.setAttribute("logonUser",user);

            //返回网站首页
            return "redirect:/";
        }

        //注册界面
        @GetMapping("register")
        public  String toRegisterPage(Model model){
            //创建一个用于绑定数据的空对象放入模型
            model.addAttribute("user",new User());
            //返回注册页面，让用户注册
            return "user_page/user_register_page";
        }

        //注册处理
        @PostMapping(value = "register" )
        public String register(User user, Model model, MultipartFile userpic) throws Exception{

            //对用户注册密码进行MD5加密
            String encodePwd= DigestUtils.md5DigestAsHex(user.getUserPwd().getBytes());
            //将加密后的密码放进用户变量
            user.setUserPwd(encodePwd);
            //单独处理提交的头像图片，存在时读入，不存在时保持空
            if(!userpic.isEmpty()) {
                //MultipartFile 的getBytes方法可以直接取出字节数组
                user.setUserPic(userpic.getBytes());
            }
            try {
                //创建用户
                userService.createUser(user);
           }catch (DataAccessException e){
                //向模型中回填错误信息，并返回登录界面，相关字段显示错误信息
                model.addAttribute("errmsg",e.getMessage());
                //返回注册界面
                return "user_page/user_register_page";
            }

            String noEncodeStr=user.getUserName()+"#"+(new Date()).getTime();
            String encodeStr=encodeCheckStr(noEncodeStr);
            try {
                sendEmail.sendActiveUserEmail(encodeStr, user.getUserEmail(),user.getUserName());
            }catch(Exception e){
                return "user_page/user_send_email_false_page" ;
            }


            //重定向至注册成功界面，重定向：防止表单重复提交
            return "redirect:/user/registerSuccess";
        }

        //注册成功界面
        @RequestMapping("registerSuccess")
        public String registerSuccess(){

            //返回注册成功界面
            return "user_page/user_register_success_page";
        }

        @GetMapping("activeUser")
        public String activeUser(String activeCode){
            String[] info=null;
            try {
                byte[] deStr = Base64Utils.decodeFromUrlSafeString(activeCode);
                String dec = new String(deStr);
                String decode = stringEncryptor.decrypt(dec);
                 info = decode.split("#");
            } catch(RuntimeException e){
           return "user_page/user_active_str_no_valid_page";
    }
            long strTime=Long.parseLong(info[1]);
            Date nowTime=new Date();
            long time=(nowTime.getTime()/1000)-(strTime/1000);
            if(time>180){
                return "user_page/user_active_str_no_valid_page";
            }
            else{
                User acUser=userService.getUserByName(info[0]);
                acUser.setUserStatus(0);
                userService.updateUserStatus(acUser);
                return "user_page/user_active_success_page";
            }
        }


        //用户中心界面
        @RequestMapping("userCenter")
        public String userCenter(Model model,HttpSession session) {
            //读取session中的用户信息
            User logonUser = (User) session.getAttribute("logonUser");
            //向模型中添加用户的完整信息
            model.addAttribute("user", userService.getUserByName(logonUser.getUserName()));
            //返回用户中心界面
            return "user_page/user_center_page";
        }

        //用户信息显示界面
        @RequestMapping("userInfo")
        public String userInfo(Model model,HttpSession session) {
            //读取session中的用户信息
            User logonUser = (User) session.getAttribute("logonUser");
            //向模型中添加用户信息
            model.addAttribute("user", userService.getUserByName(logonUser.getUserName()));
            //返回用户中心界面
            return "user_page/user_info_page";
        }

        //更新界面
        @GetMapping("updateInfo")
        public String toUpdate(Model model,HttpSession session){
            //从session提取用户信息，根据用户名提取用户完整数据，以便回填
            User preUpdateUser=(User)session.getAttribute("logonUser");
            model.addAttribute("preUpdateUser",userService.getUserByName(preUpdateUser.getUserName()));
            return "user_page/user_update_page";
        }

        //更新处理
        @PostMapping("updateInfo")
        public String updateInfo(User user,Model model,HttpSession session,MultipartFile userpic){

            //上传新图，新图处理，保存图片
            if(!userpic.isEmpty()){

                try {
                    user.setUserPic(userpic.getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else {
                //原图回填
                user.setUserPic(userService.getUserPic(user.getUserId()));
            }
            try {
                userService.updateUserInfo(user);
            }catch (DataAccessException e){
                User preUpdateUser=(User)session.getAttribute("logonUser");
                //回填错误信息
                model.addAttribute("errmsg",e.getMessage());
                //回填用户具体信息
                model.addAttribute("preUpdateUser",userService.getUserByName(preUpdateUser.getUserName()));
                //再次返回更新界面
                return "user_page/user_update_page";
            }

            //更新表示更新状态的值
            session.setAttribute("isUpdateInfo",1);
            return "redirect:/user/changeInfoSucceed";
        }

        //更新信息成功界面
        @RequestMapping("changeInfoSucceed")
        public  String changeInfoSucceed(){

            //返回更新信息成功界面
            return "user_page/user_change_info_success_page";
        }

        //更新密码界面
        @GetMapping("updatePwd")
        public String toUpdatePwd(Model model,HttpSession session){
            User preUpdateUser=(User)session.getAttribute("logonUser");

            //获取并回填用户信息，底层DAO方法已经屏蔽密码，不会取出
            model.addAttribute("preUpdateUser",userService.getUserByName(preUpdateUser.getUserName()));

            return "user_page/user_update_pwd_page";
        }
         @PostMapping("checkCurrentPwd")
         @ResponseBody
         public String checkCurrentPwd(String uid,String pwd){

            Integer id;
            try{
                id=Integer.parseInt(uid);
            }catch (NumberFormatException e){
                return "false";
            }
            User u=userService.getUserById(id);
            if(u==null) {
                return "false";
            }
            else{
                String currentPwd = u.getUserPwd();
                if(currentPwd.equals(pwd)){
                   return "true";
                }
                else
                    return "false";
            }
         }
         @RequestMapping("forgotPwd")
         public String forgotPwd(){
            return "user_page/user_pre_reset_pwd";
         }

        @PostMapping("findUserByEmail")
        @ResponseBody
        public String foundUserByEmail(String uemail){
            if(userService.getEmailList()!=null){
                List<String> elist=userService.getEmailList();
                for(String e:elist){
                    if(e.equals(uemail))
                        return "true";
                    else ;
                }
                return "false";
            }
            return "false";
        }

         @PostMapping("checkUser")
         public String checkUser(String userEmail){
            User userInfo=userService.getUserByEmail(userEmail);
            String noEncodeStr=userInfo.getUserId()+"#"+userInfo.getUserName()+"#"+(new Date()).getTime();
            String encodeStr=encodeCheckStr(noEncodeStr);
            try {
                sendEmail.sendResetPwdEmail(encodeStr, userEmail,userInfo.getUserName());
            }catch(Exception e){
                return "user_page/user_send_email_false_page" ;
            }
             return "user_page/user_send_email_success_page";
         }

         @GetMapping("resetPwd")
         public String toResetPwd(String checkCode,Model model) throws ParseException {
             String[] info =null;
            try {
                byte[] de = Base64Utils.decodeFromUrlSafeString(checkCode);
                String dec = new String(de);
                String decode = stringEncryptor.decrypt(dec);
                info = decode.split("#");
            }catch(RuntimeException e){
                return "user_page/user_check_str_no_valid_page";
            }
             long strTime=Long.parseLong(info[2]);
             Date nowTime=new Date();
             long time=(nowTime.getTime()/1000)-(strTime/1000);
             if(time>180){
                 return "user_page/user_check_str_no_valid_page";
             }

             User user=new User();
             user.setUserId(Integer.parseInt(info[0]));
             user.setUserName(info[1]);
             model.addAttribute("preResetPwdUser",user);
            return "user_page/user_reset_pwd_page";
         }

        @PostMapping("resetPwd")
        public String resetPwd(User user){
            //获取并加密密码
            String encodePwd= DigestUtils.md5DigestAsHex(user.getUserPwd().getBytes());
            //设置密码
            user.setUserPwd(encodePwd);
            //更改密码
            userService.updateUserPwd(user);

            return "user_page/user_reset_pwd_success_page";

        }


        //只适用于注册
         @PostMapping("checkDuplicateUserName")
         @ResponseBody
         public String checkDuplicateUserName(String uname){
            if(userService.getNameList()!=null){
                List<String> nlist=userService.getNameList();
                for(String n:nlist){
                    if(n.equals(uname))
                        return "false";
                    else ;
                }
                return "true";
            }
            return "true";
         }
        //只适用于注册
        @PostMapping("checkDuplicateUserEmail")
    @ResponseBody
    public String checkDuplicateUserEmail(String uemail){
        if(userService.getEmailList()!=null){
            List<String> elist=userService.getEmailList();
            for(String e:elist){
                if(e.equals(uemail))
                    return "false";
                else ;
            }
            return "true";
        }
        return "true";
    }



        //只适用于注册
        @PostMapping("checkDuplicateUserPhone")
        @ResponseBody
        public String checkDuplicateUserPhone(String uphone){
            if(userService.getPhoneList()!=null){
                List<String> plist=userService.getPhoneList();
                for(String p:plist){
                    if(p.equals(uphone))
                        return "false";
                    else ;
                }
                return "true";
            }
            return "true";
        }

        //更新密码
        @PostMapping("updatePwd")
        public String updatePwd(User user){
            //获取并加密密码
            String encodePwd= DigestUtils.md5DigestAsHex(user.getUserPwd().getBytes());
            //设置密码
            user.setUserPwd(encodePwd);
            //更改密码
            userService.updateUserPwd(user);
            //            //返回更新密码成功界面
            try {
                sendEmail.sendPwdChangeEmail(
                        userService.getUserById(user.getUserId()).getUserEmail(), user.getUserName());
            }catch (Exception e){
                logger.error("发送邮件失败，请检查！");
            }
            return "redirect:/user/changePwdSucceed";

        }

        //更新密码成功界面
        @RequestMapping("changePwdSucceed")
        public  String changePwdSucceed(HttpSession session){
                //更改密码后强制退出重新登录
                if(session!=null){
                    session.removeAttribute("logonUser");
                }
                return "user_page/user_change_pwd_success_page";
        }


        //我的收藏功能
        @RequestMapping("myLike")
        public String myLike(HttpSession session,Model model){
            User logonUser=(User)session.getAttribute("logonUser");
            List<LikeObject> myLikeRecord=likeObjectService.getUserLikeRecordByUserId(logonUser.getUserId());
            List<Work> myLikeWork=new ArrayList<Work>();
            if(!myLikeRecord.isEmpty()){
                for(LikeObject l:myLikeRecord){
                    Work single=workService.getWorkById(l.getLikeWorkId());
                    if(single!=null) {
                        myLikeWork.add(single);
                    }
                }
            }
            model.addAttribute("myLikeWork",myLikeWork);
            return "work_page/work_my_like_work_page";
        }

        //登出功能
        @RequestMapping("logout")
        public String logOut(HttpSession session){

            //销毁用户session键值
            if(session!=null){
                //session.invalidate(); 此方法会摧毁整个session，导致在管理员或用户一方登出时另一方同时登出
                session.removeAttribute("logonUser");
            }
            //返回登出界面
            return "user_page/user_logout_page";
        }

        //获取用户头像，返回字节数组，网页可以直接写在src链接里
                                                //返回图片专用类型
        @RequestMapping(value="getUserPic/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
        @ResponseBody/*必须以此返回*/
        public byte[] getUserPic(@PathVariable String id) throws Exception{

            //id不为数字时的异常处理，直接返回空(暂时无更好办法)
            try{
                Integer.parseInt(id);
            }catch(NumberFormatException e){

                return null;
            }
            //获取图片数据
            byte[] pic=userService.getUserPic(Integer.parseInt(id));
            //若获取为空，则传输默认图片到前台
            if(pic==null){
                //获取默认图片文件路径资源
                Resource resource = new ClassPathResource("static/default_pic/default_user_pic.png");
                //从中获取File类变量
                File defaultPicFile=resource.getFile();
                //读到输入流中
                FileInputStream fis=new FileInputStream(defaultPicFile);
                //读到字节数组里
                pic=new byte[fis.available()];
                fis.read(pic);
            }
            //返回数据
            return pic;
        }

        @Deprecated
        @RequestMapping(value="getUserPicByName/{name}",produces = MediaType.IMAGE_JPEG_VALUE)
        @ResponseBody
        public byte[] getUserPicByName(@PathVariable String name) throws Exception{

                User u=userService.getUserByName(name);
                if(u==null){
                    return null;
                }
                Integer id=u.getUserId();
                byte[] pic=userService.getUserPic(id);
                if(pic==null){
                Resource picResource = new ClassPathResource("static/default_pic/default_user_pic.png");
                File defaultPicFile=picResource.getFile();
                FileInputStream fis=new FileInputStream(defaultPicFile);
                pic=new byte[fis.available()];

                fis.read(pic);
            }
            return pic;
        }

        //生成验证码
        @RequestMapping(value="vericode",produces = MediaType.IMAGE_JPEG_VALUE)
        @ResponseBody
        public byte[] getVeriCode(HttpSession session){
            //获取验证码随机字符串
            String securityCode = GenString.makeString(4, false);
            //获取验证码的图像输出流
            ByteArrayInputStream imageStream = MakeVerificationCodeImage.getImageAsInputStream(securityCode);
            //存储验证码到session以便校验，且因为键相同每次刷新都会覆盖旧值
            session.setAttribute("securityCode", securityCode);
            //将图像从流中读到字节数组并返回
            byte[] img=new byte[imageStream.available()];
            imageStream.read(img,0,imageStream.available());
            return  img;
        }

        //验证码输入检测
        //Ajax方法                            //返回json类型
        @RequestMapping(value="vericodeCheck",produces="application/json")
        @ResponseBody
        public ResultInfo veriCodeCheck(String subVeriCode,HttpSession session){
            //获取存储在session里的验证码串
            String realVreiCode=(String) session.getAttribute("securityCode");
            ResultInfo result;
            //与前台提交的验证码进行比对，判断结果
            if(subVeriCode!=null){
                subVeriCode=subVeriCode.toLowerCase();
            }
            if(!realVreiCode.equals(subVeriCode)) {
                result= new ResultInfo(false, "验证码错误", null);
            }
            else
                result=  new ResultInfo(true,"验证码正确",null);
            //返回结果
            return result;

        }

    //验证码输入检测
    //Ajax方法                            //返回json类型
    @PostMapping(value="vericodeCheckJQ",produces="application/json")
    @ResponseBody
    public String veriCodeCheckJQ(String subVeriCode,HttpSession session){
        //获取存储在session里的验证码串
        String realVreiCode=(String) session.getAttribute("securityCode");
        //与前台提交的验证码进行比对，判断结果
        if(subVeriCode!=null){
            subVeriCode=subVeriCode.toLowerCase();
        }
        if(!realVreiCode.equals(subVeriCode)) {
            return "false";
        }
        else
            return "true";
        }

    @RequestMapping("requireLogin")
    public String requireLogin(HttpSession session){
        if (session.getAttribute("logonUser")!=null)
            return "redirect:/user/userCenter";
        else
            return "error_page/user_no_login_error_page";
    }

    private String encodeCheckStr(String noEncodeStr){
        String s=stringEncryptor.encrypt(noEncodeStr);
        String result= Base64Utils.encodeToUrlSafeString(s.getBytes());
        return  result;
    }


}
