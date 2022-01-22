package com.cococloud.admin.controller;


import cn.hutool.core.lang.tree.Tree;
import com.cococloud.admin.service.SysDeptService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.upms.common.entity.SysDept;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("dept")
public class SysDeptController {

    private final SysDeptService deptService;

    /**
     * 构建部门管理页面
     */
    @GetMapping("/tree")
    public CommentResult<List<Tree<Integer>>> buildMenuTree() {
        return CommentResult.ok(deptService.buildDeptTree());
    }

    /**
     * 根据菜单id查询菜单的详细信息
     */
    @GetMapping("/{deptId}")
    public CommentResult<SysDept> selectDeptById(@PathVariable("deptId") Integer deptId) {
        return CommentResult.ok(deptService.getById(deptId));
    }

    /**
     * 添加部门
     */
    @SysLog("添加部门")
    @PreAuthorize("hasAuthority('sys_dept_add')")
    @PostMapping
    public CommentResult<Boolean> saveDept(@RequestBody SysDept dept) {
        return CommentResult.ok(deptService.saveDept(dept));
    }

    /**
     * 更新部门信息
     */
    @SysLog("编辑部门")
    @PreAuthorize("hasAuthority('sys_dept_edit')")
    @PutMapping
    public CommentResult<Boolean> updateDept(@RequestBody SysDept dept) {
        return CommentResult.ok(deptService.updateDeptById(dept));
    }

    /**
     * 删除部门
     */
    @SysLog("删除部门")
    @PreAuthorize("hasAuthority('sys_dept_del')")
    @DeleteMapping("/{id}")
    public CommentResult<Boolean> deleteDept(@PathVariable("id") Integer id) {
        return CommentResult.ok(deptService.removeDeptById(id));
    }
}

