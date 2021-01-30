package com.travel.fj.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SendEmail {

    private static final String TRANS_PROTOCOL="smtp";
    private static final String HOST="smtp.qq.com";
    private static final Integer PORT=465;
    private static final Boolean IS_AUTH=true;
    private static final Boolean IS_SSL_ENABLE=true;
    private static final Boolean IS_DEBUG=true;
    private static final String SENDER_ADD="1490920024@qq.com";
    private static final String SENDER_PWD="rcjmdxqvdjgpiggc";
    private static final String TEST_STR="POSIX";

    private Properties getEmailProperties(){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", TRANS_PROTOCOL);// 连接协议
        properties.put("mail.smtp.host",HOST );// 主机名
        properties.put("mail.smtp.port",PORT);// 端口号
        properties.put("mail.smtp.auth", IS_AUTH);
        properties.put("mail.smtp.ssl.enable", IS_SSL_ENABLE);// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", IS_DEBUG);// 设置是否显示debug信息 true 会在控制台显示相关信息

        return properties;
    }


    @Async
    public void sendResetPwdEmail(String encodeUserInfo, String userEmail,String userName) throws Exception{

        Properties properties=getEmailProperties();
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);

        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(SENDER_ADD));
        // 设置收件人邮箱地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));//一个收件人
        // 设置邮件标题
        message.setSubject("重置密码！");
        // 设置邮件内容
        message.setContent("<p>尊敬的"+userName+",您正在重置密码,请点击<a href='http://localhost:8080/user/resetPwd?checkCode="+encodeUserInfo+"'>此处</a></p>重置密码," +
                "如未进行此操作，请忽略本邮件","text/html;charset=utf-8");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(SENDER_ADD, SENDER_PWD);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();


    }

    @Async
    public void sendActiveUserEmail(String encodeRegInfo, String userEmail,String userName) throws Exception{
        Properties properties=getEmailProperties();
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(SENDER_ADD));
        // 设置收件人邮箱地址
        //message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));//一个收件人
        // 设置邮件标题
        message.setSubject("开通帐户！");
        // 设置邮件内容
        message.setContent("<p>尊敬的"+userName+",您正在开通账户,请点击<a href='http://localhost:8080/user/activeUser?activeCode="+encodeRegInfo+"'>此处</a></p>开通","text/html;charset=utf-8");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(SENDER_ADD, SENDER_PWD);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();


    }

    @Async
    public void sendPwdChangeEmail(String userEmail,String userName) throws Exception{

        Properties properties=getEmailProperties();
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);

        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(SENDER_ADD));
        // 设置收件人邮箱地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));//一个收件人
        // 设置邮件标题
        message.setSubject("更改密码！");
        // 设置邮件内容
        message.setContent("<p>尊敬的"+userName+",您正在修改密码," +
                "如果您并未执行过此操作则说明您的帐户已经出现安全性问题，" +
                "请联系管理员进行处理","text/html;charset=utf-8");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect(SENDER_ADD, SENDER_PWD);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();


    }
}
