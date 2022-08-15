package com.bill.cloud.handler;

import com.alibaba.fastjson.JSON;
import com.bill.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Author huangxiaotao
 * @Date 2022/7/13 22:08
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //处理无权限
        Result<Object> result = Result.failure(HttpStatus.FORBIDDEN.value(), "用户暂无权限",null);
        //异常处理
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter().print(JSON.toJSONString(result));
    }
}
