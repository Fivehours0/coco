package com.cococloud.security;

import com.cococloud.security.component.IgnoreUrlProperties;
import com.cococloud.security.component.SecurityIgnoreUrlAspect;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.servlet.http.HttpServletRequest;

@EnableAspectJAutoProxy
@AllArgsConstructor
@EnableConfigurationProperties(IgnoreUrlProperties.class)
@Configuration(proxyBeanMethods = false)
public class CommonSecurityAutoConfiguration {

    private final HttpServletRequest request;

    @Bean
    public SecurityIgnoreUrlAspect securityIgnoreUrlAspect() {
        return new SecurityIgnoreUrlAspect(request);
    }
}
