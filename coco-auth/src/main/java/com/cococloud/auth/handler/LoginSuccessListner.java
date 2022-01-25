package com.cococloud.auth.handler;

import cn.hutool.core.collection.CollUtil;
import com.cococloud.log.event.EventPublisher;
import com.cococloud.log.event.SysLogEvent;
import com.cococloud.log.util.SysLogUtils;
import com.cococloud.upms.common.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class LoginSuccessListner implements ApplicationListener<AuthenticationSuccessEvent> {

    private final EventPublisher publisher;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (CollUtil.isNotEmpty(authentication.getAuthorities())) {
            log.info("用户：{} 登录成功", authentication.getPrincipal());
            SysLog logVo = SysLogUtils.getSysLog();
            logVo.setTitle("登录成功");
            // 发送异步日志事件
            Long startTime = System.currentTimeMillis();
            Long endTime = System.currentTimeMillis();
            logVo.setTime(endTime - startTime);
            logVo.setCreateBy(authentication.getName());
            logVo.setUpdateBy(authentication.getName());
            publisher.publishEvent(new SysLogEvent(logVo));
        }
    }
}

