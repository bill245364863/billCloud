package com.bill.cloud.config;

import com.bill.cloud.filter.JwtAuthenticationTokenFilter;
import com.bill.cloud.handler.AccessDeniedHandlerImpl;
import com.bill.cloud.handler.AuthenticationEntyPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 15:44
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;


    @Autowired
    private AuthenticationEntyPointImpl authenticationEntyPoint;
    /**
     * security config
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
               //关闭csrf
               .csrf().disable()
               //不通过session获取securitycontext
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               //对于登录接口 允许匿名访问
               .antMatchers("/user/login").anonymous()
               //除上面外的所有请求全部鉴权认证
               .anyRequest().authenticated();
        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //添加处理器
        http.exceptionHandling()
                //无权限处理器
                .accessDeniedHandler(accessDeniedHandler)
                //用户401处理器
                .authenticationEntryPoint(authenticationEntyPoint);
        //允许跨域
        http.cors();
    }

    /**
     * 认证管理类
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
