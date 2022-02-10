package com.cococloud.admin.component;

import com.cococloud.security.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class CocoResourceTokenService implements ResourceServerTokenServices {

    private final RedisTokenStore redisTokenStore;

    private final UserDetailsService userDetailsService;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication oAuth2Authentication = redisTokenStore.readAuthentication(accessToken);
        if (oAuth2Authentication == null) {
            return null;
        }

        if (!(oAuth2Authentication.getPrincipal() instanceof User)) {
            return oAuth2Authentication;
        }
        User user = (User) oAuth2Authentication.getPrincipal();

        UserDetails userDetails;
        try {
            // 需要重新获取一下，如果不重新获取，可能会导致缓存与数据库的不一致
            // 比如更新了用户的权限，但是需要重新登录才能有效果
            userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException notFoundException) {
            throw new UnauthorizedException(String.format("%s username not found", user.getUsername()),
                    notFoundException);
        }
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                userDetails, "N/A", userDetails.getAuthorities());
        OAuth2Authentication authentication = new OAuth2Authentication(
                oAuth2Authentication.getOAuth2Request(), userToken);
        return authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }
}
