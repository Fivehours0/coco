package com.cococloud.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.admin.service.SysOauthClientDetailsService;
import com.cococloud.common.util.CommentResult;
import com.cococloud.upms.common.dto.SysLogDTO;
import com.cococloud.upms.common.entity.SysLog;
import com.cococloud.upms.common.entity.SysOauthClientDetails;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
    public CommentResult<IPage<SysOauthClientDetails>> selectLogPage(Page<SysOauthClientDetails> page) {
        return CommentResult.ok(clientDetailsService.selectClientPage(page));
    }
}

