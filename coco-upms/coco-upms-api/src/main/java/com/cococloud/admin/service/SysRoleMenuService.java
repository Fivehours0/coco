package com.cococloud.admin.service;

import com.cococloud.upms.common.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    boolean saveRoleMenuByRoleId(Integer roleId, String menuIds);
}
