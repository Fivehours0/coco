package com.cococloud.admin.service;

import com.cococloud.upms.common.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据角色id，移除角色以及角色相应的菜单
     * @param id
     * @return
     */
    boolean removeRoleById(Integer id);
}
