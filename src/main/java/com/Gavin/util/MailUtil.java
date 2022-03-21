package com.Gavin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: Gavin
 * @description:
 * @className: MailUtil
 * @date: 2022/3/4 8:49
 * @version:0.1
 * @since: jdk14.0
 */
@Component
public class MailUtil {     //MailUtil目的是发送邮箱验证码

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    private static final String FROM="图书管理系统<swimmingsure@163.com>";

    public void sendCodeEmail(String toEmail,String code){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(toEmail);
        message.setSubject("邮箱登录验证码");
        message.setText("您的验证码："+code+"2分钟内有效");
        javaMailSender.send(message);
    }

    //发送带html相关内容的数据
    public void sendActiveMail(String toEmail,String activeMsg){
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
            helper.setFrom(FROM);
            helper.setSubject("邮箱激活链接");
            helper.setText(activeMsg,true);
            helper.setTo(toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }
}
