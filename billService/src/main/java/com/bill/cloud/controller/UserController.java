package com.bill.cloud.controller;

import com.bill.cloud.service.LoginService;
import com.bill.entity.SysUser;
import com.bill.util.Result;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 10:13
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
//    private static final Log log = LogFactory.get();
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param sysUser
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody SysUser sysUser){
        return  loginService.login(sysUser);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(){
        return loginService.logout();
    }
}
