package com.cococloud.gateway.config;

import com.cococloud.gateway.filter.CaptchaVerificationFilter;
import com.cococloud.gateway.filter.GatewayGlobalFilter;
import com.cococloud.gateway.filter.PasswordDecodeFilter;
import com.cococloud.gateway.handler.CaptchaHandler;
import com.cococloud.gateway.handler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author dzh
 */
@Configuration
@EnableConfigurationProperties(CocoGateWayProperties.class)
public class GateWayConfiguration {
    @Bean
    public CaptchaHandler captchaHandler (RedisTemplate redisTemplate) {
        return new CaptchaHandler(redisTemplate);
    }

    @Bean
    public CaptchaVerificationFilter captchaVerificationFilter (ObjectMapper objectMapper, RedisTemplate redisTemplate) {
        return new CaptchaVerificationFilter(objectMapper, redisTemplate);
    }

    @Bean
    public PasswordDecodeFilter passwordDecodeFilter (CocoGateWayProperties configProperties) {
        return new PasswordDecodeFilter(configProperties);
    }

    @Bean
    public GlobalFilter customFilter() {
        return new GatewayGlobalFilter();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
        return new GlobalExceptionHandler(objectMapper);
    }
}
