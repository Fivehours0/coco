package com.cococloud.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.service.SysRoleMenuService;
import com.cococloud.admin.service.SysRoleService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.upms.common.entity.SysRole;
import com.cococloud.upms.common.vo.RoleVo;
import com.cococloud.upms.common.vo.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class SysRoleController {

    private final SysRoleService roleService;

    private final SysRoleMenuService roleMenuService;

    /**
     * 构建角色管理页面
     */
    @GetMapping("/page")
    public CommentResult<IPage<SysRole>> selectUserPageVo(Page<SysRole> page) {
        return CommentResult.ok(roleService.page(page));
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    public CommentResult<List<SysRole>> selectRoleList() {
        return CommentResult.ok(roleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 添加角色
     */
    @SysLog("添加角色")
    @PreAuthorize("hasAuthority('sys_role_add')")
    @PostMapping
    public CommentResult<Boolean> saveRole(@Valid @RequestBody SysRole role) {
        return CommentResult.ok(roleService.save(role));
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @PreAuthorize("hasAuthority('sys_role_del')")
    @DeleteMapping("/{id}")
    public CommentResult<Boolean> deleteRole(@PathVariable("id") Integer id) {
        return CommentResult.ok(roleService.removeById(id));
    }

    /**
     * 更新角色权限
     */
    @SysLog("编辑角色菜单")
    @PreAuthorize("hasAuthority('sys_role_perm')")
    @PutMapping("/menu")
    public CommentResult<Boolean> updateRoleMenu(@RequestBody RoleVo roleVo) {
        return CommentResult.ok(roleMenuService.saveRoleMenuByRoleId(roleVo.getRoleId(),
                roleVo.getMenuIds()));
    }

    /**
     * 编辑角色信息
     */
    @SysLog("编辑角色信息")
    @PreAuthorize("hasAuthority('sys_role_edit')")
    @PutMapping
    public CommentResult<Boolean> updateRoleInfo(@RequestBody SysRole role) {
        return CommentResult.ok(roleService.updateById(role));
    }
}

