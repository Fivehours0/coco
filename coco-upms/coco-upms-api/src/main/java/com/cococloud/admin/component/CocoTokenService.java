package com.cococloud.admin.component;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

/**
 * token保存在redis中，所以，从redis中去获取信息
 */
@Component
@AllArgsConstructor
public class CocoTokenService implements ResourceServerTokenServices {

    private final RedisTokenStore redisTokenStore;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
//        TODO: pig中对token中携带的user信息进行了进一步的检查，即去数据库中查找是否存在当前的user。SysUserController中的loadUserInfo会用到user
//        信息，在这里对user信息进行进一步检查，可以有利于后面的步骤。
        return redisTokenStore.readAuthentication(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}
