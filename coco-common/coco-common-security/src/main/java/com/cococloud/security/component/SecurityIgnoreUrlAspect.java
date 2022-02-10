package com.cococloud.security.component;

import cn.hutool.core.util.StrUtil;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.security.annotation.SecurityIgnoreUrl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@AllArgsConstructor
public class SecurityIgnoreUrlAspect {

    private final HttpServletRequest request;

    @SneakyThrows
    @Around("@annotation(securityIgnoreUrl)")
    public Object around(ProceedingJoinPoint pjp, SecurityIgnoreUrl securityIgnoreUrl) {

        boolean value = securityIgnoreUrl.value();
        String inner = request.getHeader(SecurityConstants.HEAD_INNER);
        if (value && !StrUtil.equals(SecurityConstants.INNER_VALUE, inner)) {
            log.warn("访问接口 {} 没有权限", pjp.getSignature().getName());
            throw new AccessDeniedException("Access is denied");
        }

        return pjp.proceed();
    }
}
