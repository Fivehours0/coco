package com.cococloud.upms.common.vo;

import com.cococloud.upms.common.entity.SysUser;
import lombok.Data;

/**
 * 首先需要获取用户的角色信息和权限信息，通过角色信息和权限信息来角色用户可以访问哪些menu
 */
@Data
public class UserInfoVo {
    /**
     * 用户基本信息
     */
    private SysUser sysUser;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Integer[] roles;
}
