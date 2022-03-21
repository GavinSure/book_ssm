package com.Gavin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Gavin
 * @description:
 * @className: EmailController
 * @date: 2022/3/3 22:41
 * @version:0.1
 * @since: jdk14.0
 */

@RestController
public class EmailController {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @GetMapping("/send1")
    public String email1(){
        SimpleMailMessage mailMessage =new SimpleMailMessage();

        mailMessage.setFrom("swimmingsure@163.com");
        mailMessage.setTo("2472541482@qq.com");
        mailMessage.setSubject("一刀99999");
        mailMessage.setText("屠龙宝刀点击就送");
        javaMailSender.send(mailMessage);
        return "success";
    }
}
