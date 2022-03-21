package com.Gavin.controller;

/**
 * @author: Gavin
 * @description:
 * @className: EmailController
 * @date: 2022/3/3 22:41
 * @version:0.1
 * @since: jdk14.0
 */

import com.Gavin.common.AsycThreadPoolManager;
import com.Gavin.common.ServerResponse;
import com.Gavin.common.StatusEnum;
import com.Gavin.entity.Admin;
import com.Gavin.exception.MyRuntimeException;
import com.Gavin.service.AdminService;
import com.Gavin.util.MailUtil;
import com.Gavin.util.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Controller                 //因为有跳转操作无法使用restcontroller注解
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private TokenService tokenService;

    //验证码登录
    @PostMapping("/codeLogin")
    @ResponseBody
    public ServerResponse<?> codeLogin(String email,String code){
        Jedis resource=jedisPool.getResource();
        String redisCode = resource.get(email);
        if (redisCode==null){
            throw new MyRuntimeException(StatusEnum.CODE_TIMEOUT);
        }
        if (!redisCode.equals(code))
            throw new MyRuntimeException(StatusEnum.CODE_ERROR);
        Admin admin=adminService.findAdminByEmail(email).get(0);
        //清除缓存的数据
        resource.del(email);
        //登陆成功------>服务器生成token
        String token = tokenService.createToken(admin.getAdminId());
        //将token数据响应客户端
        return ServerResponse.success(token);
    }

    //激活用户状态功能
    @GetMapping("/activeAdmin/{id}")
    public String activeAdmin(@PathVariable long id){
        Admin admin=adminService.findAdminById(id);
        admin.setActive(true);
        adminService.updateAdminById(admin);
        return "redirect:http://127.0.0.1:8848/book_page/success.html";
    }

    @GetMapping("sendActiveEmail")
    @ResponseBody
    public ServerResponse<?> sendActiveEmail(String email){
        List<Admin> adminList=adminService.findAdminByEmail(email);
        if (adminList==null||adminList.isEmpty()){
            throw new MyRuntimeException(StatusEnum.ADMIN_NOT_EXITS);
        }
        Admin admin=adminList.get(0);
        if (admin.getActive()){
            throw new MyRuntimeException(StatusEnum.ADMIN_ALREADY_ACTIVE);
        }
        //发送激活邮件
        String activeMsg ="请点击下方链接激活:<br>  <a href='http://127.0.0.1:8080/admin/activeAdmin/"+admin.getAdminId()+"'>点我激活</a>";
        AsycThreadPoolManager.getInstance().executeTask(()->mailUtil.sendActiveMail(email,activeMsg));
        return ServerResponse.success();
    }

    @GetMapping("/adminLogin")
    @ResponseBody
    public ServerResponse<String> adminLogin(String email){
        //登录
        //1.根据email查询数据库
        List<Admin> adminList=adminService.findAdminByEmail(email);
        //1.1  为null  系统不存在此用户
        if (adminList==null||adminList.isEmpty()){
            throw new MyRuntimeException(StatusEnum.ADMIN_NOT_EXITS);
        }
        //1.2 不为null
        // 邮箱号状态: 未激活 必须先激活
        Admin admin=adminList.get(0);
        if (!admin.getActive()){
            throw new MyRuntimeException(StatusEnum.ADMIN_NOT_ACTIVE);
        }

        //spring集成redis
        //引入redis的依赖



        //已经激活  向这个邮箱里面发送邮件
        //开启异步线程完成
        String code = UUID.randomUUID().toString().substring(0, 6);
        //spring集成redis
        Jedis jedis=jedisPool.getResource();
        jedis.set(email,code);
        jedis.expire(email,(int) Duration.ofMinutes(2).getSeconds());
        AsycThreadPoolManager.getInstance().executeTask(()->mailUtil.sendCodeEmail(email,code));
        return ServerResponse.success(admin.getAdminAvatar());
    }

    @GetMapping("/findAdminByEmail")
    @ResponseBody
    public ServerResponse<Admin> findAdminByEmail(String email){
        List<Admin> adminList = adminService.findAdminByEmail(email);
        if (adminList==null||adminList.isEmpty()){
            throw new MyRuntimeException(StatusEnum.ADMIN_NOT_EXITS);
        }
        Admin admin=adminList.get(0);
        return ServerResponse.success(admin);
    }

    //对具体的邮箱发送数据:
    //Spring集成mail开发
    //1.有邮箱  开启收发功能  开启smtp  pop3协议  生成授权码
    //2. 服务器:   smtp.163.com    xulisha_123@163.com
    //3. 引入依赖  java-mail  “sun-mail”
    //4. 在mvc的资源配置文件中  集成mail配置
}
