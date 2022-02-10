package com.cococloud.auth.config;

import cn.hutool.core.util.StrUtil;
import com.cococloud.security.component.BriefUser;
import com.cococloud.security.component.CocoUser;
import com.cococloud.auth.handler.CocoWebResponseExceptionTranslater;
import com.cococloud.common.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.*;


@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final JdbcClientDetailsService jdbcClientDetailsService;

    private final TokenStore redisTokenStore;

    private final UserDetailsService detailsService;

    private final AuthenticationManager authenticationManager;

    private final CocoWebResponseExceptionTranslater webResponseExceptionTranslater;

    /**
     * defines the authorization and token endpoints and the token services.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /*
         * /oauth/check_token：用于资源服务访问的令牌解析端点
         * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
         */
        security.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

    /**
     * a configurer that defines the client details service. Client details can be initialized,
     * or you can just refer to an existing store.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * defines the authorization and token endpoints and the token services.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(redisTokenStore).userDetailsService(detailsService).reuseRefreshTokens(false)
                 .exceptionTranslator(webResponseExceptionTranslater).tokenEnhancer(enhancer());
        setTokenGranter(endpoints);
    }

    /**
     * 添加对password模式的支持
     */
    private void setTokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        endpoints.tokenGranter(new CompositeTokenGranter(granters));
    }

    /**
     * 增强token，使token中包含额外的用户信息，以支持token管理
     */
    private TokenEnhancer enhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInformation = new HashMap<>();
            String clientId = authentication.getOAuth2Request().getClientId();
            additionalInformation.put(SecurityConstants.CLIENT_ID, clientId);

            // 客户端模式不返回用户信息
            if (StrUtil.equals(authentication.getOAuth2Request().getGrantType(), SecurityConstants.CLIENT_MODE)) {
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                return accessToken;
            }

            CocoUser details = (CocoUser) authentication.getUserAuthentication().getPrincipal();
            // 简要用户信息，除去了用户的敏感信息
            BriefUser briefUser = new BriefUser(details.getUserId(), details.getUsername());
            additionalInformation.put(SecurityConstants.DETAILS_USER, briefUser);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            return accessToken;
        };
    }
}
