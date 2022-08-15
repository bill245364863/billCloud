package com.bill.cloud.service;

import com.bill.entity.SysUser;
import com.bill.util.Result;

public interface LoginService {

    /**
     * 登录
     * @param sysUser
     * @return
     */
    Result<String> login(SysUser sysUser);

    /**
     * 登出
     * @param sysUser
     * @return
     */
    Result<String> logout();
}
