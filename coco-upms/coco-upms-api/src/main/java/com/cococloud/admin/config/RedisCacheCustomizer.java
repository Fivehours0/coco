package com.cococloud.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisCacheCustomizer implements RedisCacheManagerBuilderCustomizer {

    private RedisConnectionFactory connectionFactory;

    /**
     * 将keys扫描策略替换为scan扫描
     * @param builder 构造器
     */
    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        builder.cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(
                connectionFactory, BatchStrategies.scan(100)));
    }

    @Autowired
    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}
