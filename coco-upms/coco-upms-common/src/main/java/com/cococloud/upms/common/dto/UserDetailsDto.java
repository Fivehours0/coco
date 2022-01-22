package com.cococloud.upms.common.dto;

import com.cococloud.upms.common.entity.SysRole;
import com.cococloud.upms.common.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dzh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    /**
     * 用户基本信息
     */
    private SysUser sysUser;

    /**
     * 角色集合
     */
    private List<SysRole> roleList;

    /**
     * 角色的id集合，方便业务类进行crud
     */
    private List<Integer> roleId;

    /**
     * 角色可以访问的菜单信息, {@link com.cococloud.upms.common.entity.SysMenu} 中的permission字段
     */
    private String[] permission;
}
