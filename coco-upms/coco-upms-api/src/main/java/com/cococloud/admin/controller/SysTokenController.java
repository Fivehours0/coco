package com.cococloud.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.annotation.SysLog;
import com.cococloud.security.annotation.SecurityIgnoreUrl;
import com.cococloud.upms.common.feign.RemoteTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class SysTokenController {
    private final RemoteTokenService tokenService;

    @SecurityIgnoreUrl
    @GetMapping("/page")
    public CommentResult<Page> getPageToken(Page page) {
        return tokenService.getPageToken(page, SecurityConstants.INNER_VALUE);
    }

    @SysLog("删除令牌")
    @SecurityIgnoreUrl
    @PreAuthorize("hasAuthority('sys_token_del')")
    @DeleteMapping("/{token}")
    public CommentResult<Boolean> removeToken(@PathVariable("token") String token) {
        return tokenService.removeToken(token, SecurityConstants.INNER_VALUE);
    }
}
