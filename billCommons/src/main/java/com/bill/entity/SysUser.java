package com.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author huangxiaotao
 * @Date 2022/6/8 10:17
 **/
@TableName("sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    @TableId
    private Long id;
    @TableField("user_name")
    private String userName;
    @TableField("nick_name")
    private String nickName;
    @TableField("password")
    private String passwd;
    private String sex;
}
