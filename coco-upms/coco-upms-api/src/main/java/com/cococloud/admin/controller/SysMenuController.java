package com.cococloud.admin.controller;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cococloud.admin.service.SysMenuService;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.upms.common.entity.SysMenu;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("menu")
public class SysMenuController {

    private final SysMenuService menuService;

    /**
     * 获取该角色对应的所有菜单
     */
    @GetMapping
    public CommentResult<List<Tree<Integer>>> getMenu(Integer parentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        List<Integer> roleId = null;
        // 获取角色id
        if (principal instanceof User) {
            roleId = ((User) principal).getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(str -> StrUtil.startWith(str, SecurityConstants.ROLE_PREFIX))
                    .map(str -> Integer.parseInt(StrUtil.removePrefix(str, SecurityConstants.ROLE_PREFIX)))
                    .collect(Collectors.toList());
        }
        if (roleId == null) {
            return CommentResult.failed("角色信息不存在");
        }
        List<SysMenu> menus = roleId.stream()
                .map(menuService::loadMenuByRoleId).flatMap(Collection::stream).collect(Collectors.toList());
        return CommentResult.ok(menuService.buildLeftMenuTree(menus, parentId));
    }

    /**
     * 构建菜单管理页面
     */
    @GetMapping("/tree")
    public CommentResult<List<Tree<Integer>>> buildMenuTree(Integer parentId, boolean lazy) {
        return CommentResult.ok(menuService.buildMenuTree(parentId, lazy));
    }

    /**
     * 根据id获取菜单信息
     */
    @GetMapping("/{id}")
    public CommentResult<SysMenu> selectMenuById(@PathVariable("id") Integer menuId) {
        return CommentResult.ok(menuService.getOne(Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getMenuId, menuId)));
    }

    /**
     * 根据角色id构建属性菜单
     */
    @GetMapping("/tree/{id}")
    public CommentResult<List<Integer>> buildMenuTree(@PathVariable("id") Integer roleId) {
        return CommentResult.ok(menuService.loadMenuByRoleId(roleId).stream()
                .map(SysMenu::getMenuId).collect(Collectors.toList()));
    }

    /**
     * 添加菜单
     */
    @SysLog("添加菜单")
    @PreAuthorize("hasAuthority('sys_menu_add')")
    @PostMapping
    public CommentResult<SysMenu> saveMenu(@RequestBody SysMenu menu) {
        menuService.save(menu);
        return CommentResult.ok(menu);
    }

    /**
     * 更新菜单
     */
    @SysLog("编辑菜单")
    @PreAuthorize("hasAuthority('sys_menu_edit')")
    @PutMapping
    public CommentResult<Boolean> updateMenu(@RequestBody SysMenu menu) {
        return CommentResult.ok(menuService.updateMenuById(menu));
    }

    /**
     * 删除菜单
     */
    @SysLog("删除菜单")
    @PreAuthorize("hasAuthority('sys_menu_del')")
    @DeleteMapping("/{id}")
    public CommentResult<Boolean> deleteMenu(@PathVariable("id") Integer menuId) {
        return CommentResult.ok(menuService.deleteMenuById(menuId));
    }
}

