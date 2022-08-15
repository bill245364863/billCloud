package com.bill.cloud.handler;

import com.alibaba.fastjson.JSON;
import com.bill.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author huangxiaotao
 * @Date 2022/7/13 21:59
 **/
@Component
public class AuthenticationEntyPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException) throws IOException, ServletException {
        Result<Object> result = Result.failure(HttpStatus.UNAUTHORIZED.value(), "用户认证失败请重新登录",null);
        //异常处理
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().print(JSON.toJSONString(result));
    }
}
