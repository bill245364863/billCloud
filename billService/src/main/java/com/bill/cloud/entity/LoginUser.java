package com.bill.cloud.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.bill.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 15:26
 **/
@Data
@NoArgsConstructor
@Slf4j
public class LoginUser implements UserDetails {
    //用户
    private SysUser sysUser;
    /**
     * 权限列表
     */
    private List<String> authoritys;

    public LoginUser(SysUser sysUser, List<String> authoritys) {
        this.sysUser = sysUser;
        this.authoritys = authoritys;
    }
    //存储springsecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private  List<SimpleGrantedAuthority> authorities;

    /**
     * 权限列表
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("执行Authorities存储》》》》》》》》》》》》》》》");
        if (Objects.nonNull(authorities)) {
            return authorities;
        }
        authorities = authoritys.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPasswd();
    }

    @Override
    public String getUsername() {
        return sysUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
