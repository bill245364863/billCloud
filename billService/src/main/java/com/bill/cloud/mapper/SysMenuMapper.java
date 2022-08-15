package com.bill.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bill.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectPermsByUserId(Long id);
}
