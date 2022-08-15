package com.bill.cloud.controller;

import com.bill.util.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author huangxiaotao
 * @Date 2022/6/14 22:23
 **/
@RestController
public class HelloController {
    @PostMapping("/hello")
    @PreAuthorize("hasAuthority('system:test:list')") //权限认证配置
    public Result<String> hello(){
        return Result.success("hello");
    }
}
