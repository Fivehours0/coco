package com.cococloud.admin.config;

import com.cococloud.common.constant.CacheConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class CocoRedisTokenStoreConfiguration {
    /**
     * 授权服务器中使用RedisTokenStore存储token信息
     * 在资源服务器中，使用redisTokenStore对请求中携带的token进行检查
     */
    @Bean
    public RedisTokenStore redisTokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        redisTokenStore.setPrefix(CacheConstants.PROJECT_OAUTH_ACCESS);
        return redisTokenStore;
    }
}
