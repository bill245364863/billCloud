package com.bill.cloud.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import com.alibaba.fastjson.JSONObject;
import com.bill.cloud.constant.SysConstant;
import com.bill.cloud.entity.LoginUser;
import com.bill.cloud.service.LoginService;
import com.bill.entity.SysUser;
import com.bill.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 16:08
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${jwt.key}")
    private String jwtKey;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public Result<String> login(SysUser sysUser) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPasswd());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        //使用jwt生产 token
        LoginUser user = (LoginUser)authenticate.getPrincipal();
        String userInfo = JSONObject.toJSONString(user);
//        String userId = user.getSysUser().getId().toString();
        //签发时间 --- 生效时间 --- 失效时间
        String data = JWT.create()
                .setIssuedAt(DateUtil.date()) //签发时间
                .setExpiresAt(DateUtil.offsetMinute(DateUtil.date(),10)) //失效时间
                .setPayload("token", userInfo)
                .setKey(jwtKey.getBytes())
                .sign();
        //存入缓存
        redisTemplate.boundValueOps(SysConstant.sys_user_token + user.getSysUser().getId()).set(data);
        return Result.success("调用成功",data);
    }


    @Override
    public Result<String> logout() {
        //获取SecurityContextFilter中的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //删除redis中值
        redisTemplate.delete(SysConstant.sys_user_token + loginUser.getSysUser().getId());
        return Result.success("logout success");
    }
}
