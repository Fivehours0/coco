package com.cococloud.admin.config;

import com.cococloud.common.constant.CacheConstants;
import com.cococloud.security.component.IgnoreUrlProperties;
import com.cococloud.security.service.CocoUserDetailsServiceImpl;
import com.cococloud.upms.common.feign.RemoteUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAspectJAutoProxy
@AllArgsConstructor
public class CocoResourceAutoConfiguration {

    private final CacheManager cacheManager;

    @Bean
    public RedisTokenStore redisTokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        redisTokenStore.setPrefix(CacheConstants.PROJECT_OAUTH_ACCESS);
        return redisTokenStore;
    }

    @Bean
    public UserDetailsService userDetailsService(RemoteUserDetail remoteUserDetail) {
        return new CocoUserDetailsServiceImpl(remoteUserDetail, cacheManager);
    }
}
