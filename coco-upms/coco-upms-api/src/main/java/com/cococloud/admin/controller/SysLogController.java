package com.cococloud.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.service.SysLogService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
public class SysLogController {

    private final SysLogService logService;

    /**
     * 日志管理页面获取日志信息
     */
    @GetMapping("/page")
    public CommentResult<IPage<SysLog>> selectLogPage(Page<SysLog> page, SysLogDTO logDTO) {
        return CommentResult.ok(logService.selectLogPage(page, logDTO));
    }

    /**
     * 根据id删除日志
     */
    @PreAuthorize("hasAuthority('sys_log_del')")
    @DeleteMapping("/{id}")
    public CommentResult<Boolean> removeLogById(@PathVariable Integer id) {
        return CommentResult.ok(logService.removeById(id));
    }

    /**
     * 保存日志
     */
    @PostMapping
    public CommentResult<Boolean> saveLog(@Valid @RequestBody SysLog log) {
        return CommentResult.ok(logService.save(log));
    }
}

