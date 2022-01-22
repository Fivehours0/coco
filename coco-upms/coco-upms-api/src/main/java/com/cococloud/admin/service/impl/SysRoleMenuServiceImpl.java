package com.cococloud.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.upms.common.entity.SysRoleMenu;
import com.cococloud.admin.mapper.SysRoleMenuMapper;
import com.cococloud.admin.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoleMenuByRoleId(Integer roleId, String menuIds) {
        this.remove(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getRoleId, roleId));
        if (StrUtil.isBlank(menuIds)) {
            return true;
        }
        List<SysRoleMenu> roleMenus = Arrays.stream(menuIds.split(StringPool.COMMA)).map(s -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(Integer.parseInt(s));
            return sysRoleMenu;
        }).collect(Collectors.toList());
        return this.saveBatch(roleMenus);
    }
}
