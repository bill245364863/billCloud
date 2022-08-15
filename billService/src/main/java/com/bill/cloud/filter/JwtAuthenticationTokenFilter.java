package com.bill.cloud.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bill.cloud.constant.SysConstant;
import com.bill.cloud.entity.LoginUser;
import com.bill.entity.SysUser;
import com.bill.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 21:29
 **/
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("Authentication");
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        try {
            JWTValidator jwtValidator = JWTValidator.of(token).validateDate(DateUtil.date());
        } catch (ValidateException e) {
            log.error("token过期{}",e.getMessage());
            filterChain.doFilter(request,response);
            return;
        }
        JWT jwt = JWT.of(token);
        String userInfo = (String)jwt.getPayload("token");
        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        LoginUser loginUser = JSON.toJavaObject(jsonObject, LoginUser.class);
        log.info("登录用户：{}",loginUser.getUsername());
        //存储redis
        String tokenCache = (String)redisTemplate.boundValueOps(SysConstant.sys_user_token + loginUser.getSysUser().getId()).get();
        if (StringUtils.isBlank(tokenCache)) {
            filterChain.doFilter(request,response);
            return;
        }
        //存入SecurityContextHolder
        //todo  获取权限信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
