package com.cococloud.admin.service;

import cn.hutool.core.lang.tree.Tree;
import com.cococloud.upms.common.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据roleId获取SysMenu
     */
    List<SysMenu> loadMenuByRoleId(Integer roleId);

    /**
     * 构建侧边栏菜单
     */
    List<Tree<Integer>> buildLeftMenuTree(List<SysMenu> menus, Integer parentId);

    /**
     * 菜单管理页面
     */
    List<Tree<Integer>> buildMenuTree(Integer parentId, Boolean lazy);

    /**
     * 根据id删除菜单
     */
    boolean deleteMenuById(Integer menuId);
}
