package com.travel.fj;

import com.travel.fj.dao.*;
import com.travel.fj.domain.*;
import com.travel.fj.queryhelper.AttracQueryHelper;
import com.travel.fj.queryhelper.GuideQueryHelper;
import com.travel.fj.queryhelper.UserQueryHelper;
import com.travel.fj.service.*;
import com.travel.fj.utils.ResultInfo;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FjApplicationTests {

    @Autowired
    WorkDao workDao;
    @Autowired
    AttractionService attractionService;

    @Autowired
    AttractionDao attractionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    GuideDao guideDao;

    @Autowired
    CityDao cityDao;

    @Autowired
    LikeObjectDao likeObjectDao;

    @Autowired
    StringEncryptor stringEncryptor;


    @Test
    public void contextLoads() {
    }

//    @Test
//    public void testWorkD(){
////        Work work=new Work();
////        work.setWorkTitle("测试游记2");
////        User user=new User();
////        user.setUserId(2);
////        work.setWorkAuthor(user);
////        work.setWorkText("<strong><del>测试游记2文本</del><strong>");
////        NoteType noteType=new NoteType();
////        noteType.setNoteTypeId(1);
////        work.setWorkType(noteType);
////        Attraction attraction=new Attraction();
////        attraction.setAttracId(2);
////        work.setWorkAttrac(attraction);
////        workDao.createWork(work);
//
//
//         // Work work=new Work();
//          //work.setWorkId(3);
////          work.setWorkTitle("测试游记1!");
////        work.setWorkText("<strong><del>测试游记1!文本</del><strong>");
////        NoteType noteType=new NoteType();
////        noteType.setNoteTypeId(2);
////        work.setWorkType(noteType);
//        //work.setWorkStatus(0);
//       // workDao.updateWorkStatus(work);
//        System.out.println( workDao.getWorkByAttracId(10).size());
//    }
//
//    @Test
//    public void loadTest(){
////        List<Work> ww=workDao.loadAllWorks();
//////        for(Work w:ww){
//////            System.out.println(w);
//////        }
////        System.out.println(workDao.getWorkById(4));
////        System.out.println(workDao.getWorkById(3));
////        System.out.println(workDao.getWorkByUserId(2));
//        //System.out.println(workDao.getWorkByUserId(1));
////        System.out.println(workDao.getWorkByWorkTypeId(1));
//        //System.out.println( cityDao.getCityByName("福州"));
////        Attraction attraction=new Attraction();
////        attraction.setAttracClickCount(1);
////        attraction.setAttracId(1);
////        attractionService.updateAttracClickCount(attraction);
//
//        List<Attraction> ll=attractionService.getRandomAttrac();
//        for(Attraction l:ll) {
//            System.out.print(l.getAttracId()+"/");
//        }
//    }
//
    @Test
   public  void queryTest(){
        AttracQueryHelper aqh=new AttracQueryHelper();
        System.out.println(attractionDao.loadQueryAttractions(aqh));
////        UserQueryHelper uqh=new UserQueryHelper();
////
////        //uqh.setQueryUserName("t2");
////        uqh.setQueryUserSex("F");
////        uqh.setQueryUserStatus(1);
////        System.out.println(userDao.loadQueryUser(uqh));
//
//        GuideQueryHelper gqh=new GuideQueryHelper();
////        gqh.setQueryGuideTitle("南测");
////        gqh.setQueryGuideAuthorId(2);
////        gqh.setQueryGuideAttracId(1);
//        gqh.setQueryGuideStatus(0);
//        System.out.println(guideDao.loadQueryGuides(gqh));
//
   }
//
//    @Test
//    public void testGuideD(){
//        Guide t=new Guide();
//        t.setGuideTitle("指南测试一");
//        Admin a=new Admin();
//        a.setAdminId(4);
//        t.setGuideAuthor(a);
//
//        GuideType g=new GuideType();
//        g.setGuideTypeId(2);
//        t.setGuideType(g);
//
//        Attraction aa=new Attraction();
//        aa.setAttracId(8);
//        t.setGuideAttrac(aa);
//
//        t.setGuideText("<a href='www.baidua.com'>土楼游览</a>");
//
//        t.setGuideId(2);
//
//        //System.out.println( guideDao.loadAllGuides());
//
////        t.setGuideId(1);
////        t.setGuideStatus(0);
////        guideDao.updateGuideStatus(t);
//
////        System.out.println( guideDao.getGuideByGuideTypeId(2));
////        System.out.println( guideDao.getGuideByGuideTypeId(1));
//        guideDao.updateGuide(t);
//
//
//    }

//    @Test
//    public void ldao(){
//        String s=stringEncryptor.encrypt("12355654");
//        String ss=Base64Utils.encodeToUrlSafeString(s.getBytes());
//        System.out.println(ss);
//        byte[] b=Base64Utils.decodeFromUrlSafeString(ss);
//        String sss=new String(b);
//        System.out.println(stringEncryptor.decrypt(sss));
//    }
//
//    @Test
//    public void sendE() throws Exception{
//        Properties properties = new Properties();
//                 properties.put("mail.transport.protocol", "smtp");// 连接协议
//                 properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
//                properties.put("mail.smtp.port", 465);// 端口号
//                properties.put("mail.smtp.auth", "true");
//                properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
//               properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
//                 // 得到回话对象
//                 Session session = Session.getInstance(properties);
//                 // 获取邮件对象
//                Message message = new MimeMessage(session);
//                 // 设置发件人邮箱地址
//                 message.setFrom(new InternetAddress("1490920024@qq.com"));
//                 // 设置收件人邮箱地址
//                //message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
//                message.setRecipient(Message.RecipientType.TO, new InternetAddress("Onilaxe@outlook.com"));//一个收件人
//                 // 设置邮件标题
//                 message.setSubject("测试！");
//                // 设置邮件内容
//                 //message.setText("邮件内容邮箱测试,邀请您访问");
//                 message.setContent("<p>邮件内容邮箱测试,邀请您访问<a href='http://localhost:8080'>随行网</a></p>","text/html;charset=utf-8");
//                // 得到邮差对象
//                 Transport transport = session.getTransport();
//                 // 连接自己的邮箱账户
//                 transport.connect("1490920024@qq.com", "rcjmdxqvdjgpiggc");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
//                 // 发送邮件
//                transport.sendMessage(message, message.getAllRecipients());
//                 transport.close();
//
//    }

//    @Test
//    public void Pagee(){
//       Ob b= new Ob();
//       b.a();
//    }

}
