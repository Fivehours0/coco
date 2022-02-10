package com.cococloud.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.admin.mapper.SysRoleMenuMapper;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.upms.common.entity.SysRole;
import com.cococloud.admin.mapper.SysRoleMapper;
import com.cococloud.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cococloud.upms.common.entity.SysRoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 通过角色ID，删除角色,并清空角色菜单缓存
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public boolean removeRoleById(Integer id) {
        sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>update().lambda().eq(SysRoleMenu::getRoleId, id));
        return this.removeById(id);
    }
}
