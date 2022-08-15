package com.bill.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bill.cloud.entity.LoginUser;
import com.bill.cloud.mapper.SysMenuMapper;
import com.bill.cloud.mapper.SysUserMapper;
import com.bill.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author huangxiaotao
 * @Date 2022/5/26 21:35
 **/
@Service
public class SysUserServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查询用户信息
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getUserName,userName);
        SysUser sysUser = sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        // todo 查询用户权限信息 封装权限信息
//        List<String> authoritys = new ArrayList<>(Arrays.asList("test","admin"));
        List<String> authoritys = sysMenuMapper.selectPermsByUserId(sysUser.getId());
        return new LoginUser(sysUser,authoritys);
    }
}
