package com.cococloud.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.service.SysOauthClientDetailsService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.upms.common.entity.SysOauthClientDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 终端信息表 前端控制器
 * </p>
 *
 * @author dzh
 * @since 2021-12-31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
public class SysOauthClientDetailsController {

    private final SysOauthClientDetailsService clientDetailsService;

    /**
     * 终端管理页面获取日志信息
     */
    @GetMapping("/page")
    public CommentResult<IPage<SysOauthClientDetails>> selectClientPage(Page<SysOauthClientDetails> page) {
        return CommentResult.ok(clientDetailsService.selectClientPage(page));
    }

    /**
     * 通过id查询client信息，判断是否存在该id的client，避免重复添加
     */
    @GetMapping("/{id}")
    public CommentResult<List<SysOauthClientDetails>> selectClientById(@PathVariable String id) {
        return CommentResult.ok(clientDetailsService.list(
                Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, id)));
    }

    /**
     * 添加client
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @SysLog("添加终端")
    @PostMapping
    @PreAuthorize("hasAuthority('sys_client_add')")
    public CommentResult<Boolean> addClient(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
        return CommentResult.ok(clientDetailsService.save(sysOauthClientDetails));
    }

    /**
     * 修改client
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @SysLog("修改终端")
    @PutMapping
    @PreAuthorize("hasAuthority('sys_client_edit')")
    public CommentResult<Boolean> updateClient(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
        return CommentResult.ok(clientDetailsService.updateById(sysOauthClientDetails));
    }

    /**
     * 删除client
     * @return success/false
     */
    @SysLog("删除终端")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys_client_del')")
    public CommentResult<Boolean> deleteClient(@PathVariable String id) {
        return CommentResult.ok(clientDetailsService.removeById(id));
    }
}

