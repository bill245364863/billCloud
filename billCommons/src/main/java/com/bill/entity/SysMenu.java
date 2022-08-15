package com.bill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author huangxiaotao
 * @Date 2022/7/6 21:52
 **/
@TableName("sys_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {
    @TableId
    private Integer id;
    private String menuName;
    private String path;
    private String component;
    private String visible;
    private String status;
    private String perms;
    private String icon;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Integer delFlag;
    private String remark;
}
