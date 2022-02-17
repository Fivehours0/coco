package com.cococloud.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.admin.mapper.SysRoleMenuMapper;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.constant.CommonConstants;
import com.cococloud.common.constant.enums.MenuTypeEnum;
import com.cococloud.upms.common.entity.SysMenu;
import com.cococloud.admin.mapper.SysMenuMapper;
import com.cococloud.admin.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cococloud.upms.common.entity.SysRoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;

    private final SysRoleMenuMapper roleMenuMapper;

    /**
     * 根据角色id查询Menu列表
     */
    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
    public List<SysMenu> loadMenuByRoleId(Integer roleId) {
        return sysMenuMapper.loadMenuByRoleId(roleId);
    }

    /**
     * 构建侧边栏菜单
     */
    @Override
    public List<Tree<Integer>> buildLeftMenuTree(List<SysMenu> menus, Integer parentId) {
        List<TreeNode<Integer>> treeMenus = menus.stream()
                .filter(menu -> StrUtil.equals(MenuTypeEnum.LEFT_MENU.getType(), menu.getType()))
                .filter(menu -> StrUtil.isNotBlank(menu.getPath())).map(getNodeFunction())
                .collect(Collectors.toList());
        parentId = parentId == null ? CommonConstants.MENU_DEFAULT_ROOT_ID : parentId;
        return TreeUtil.build(treeMenus, parentId);
    }

    /**
     * 构建菜单管理页面
     * 如果lazy为true，则根据parentId来构建菜单。如果为false，则加载全部的菜单
     */
    @Override
    public List<Tree<Integer>> buildMenuTree(Integer parentId, Boolean lazy) {
        if (!lazy) {
            List<TreeNode<Integer>> nodes = sysMenuMapper
                    .selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort)).stream()
                    .map(getNodeFunction()).collect(Collectors.toList());
            return TreeUtil.build(nodes, CommonConstants.MENU_DEFAULT_ROOT_ID);
        }
        parentId = parentId == null ? CommonConstants.MENU_DEFAULT_ROOT_ID : parentId;
        List<TreeNode<Integer>> nodes = sysMenuMapper
                .selectList(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parentId).orderByAsc(SysMenu::getSort))
                .stream().map(getNodeFunction()).collect(Collectors.toList());
        return TreeUtil.build(nodes, parentId);
    }

    /**
     * 删除菜单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public boolean deleteMenuById(Integer menuId) {
        // 查询该节点下有无子节点
        List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, menuId));
        Assert.isTrue(CollUtil.isEmpty(menuList), "菜单含有下级不能删除");
        roleMenuMapper.delete(Wrappers.<SysRoleMenu>lambdaQuery().eq(SysRoleMenu::getMenuId, menuId));
        return this.removeById(menuId);
    }

    /**
     * 更新菜单
     */
    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public boolean updateMenuById(SysMenu menu) {
        return this.updateById(menu);
    }

    private Function<SysMenu, TreeNode<Integer>> getNodeFunction() {
        return menu -> {
            TreeNode<Integer> node = new TreeNode<>();
            node.setId(menu.getMenuId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSort());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("permission", menu.getPermission());
            extra.put("path", menu.getPath());
            extra.put("icon", menu.getIcon());
            extra.put("sort", menu.getSort());
            extra.put("keepAlive", menu.getKeepAlive());
            extra.put("type", menu.getType());
            extra.put("label", menu.getName());
            node.setExtra(extra);
            return node;
        };
    }
}
