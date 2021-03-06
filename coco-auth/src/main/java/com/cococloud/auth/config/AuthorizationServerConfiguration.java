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
         * /oauth/check_token????????????????????????????????????????????????
         * /oauth/token_key?????????????????????????????????????????????JWT????????????
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
     * ?????????password???????????????
     */
    private void setTokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        endpoints.tokenGranter(new CompositeTokenGranter(granters));
    }

    /**
     * ??????token??????token??????????????????????????????????????????token??????
     */
    private TokenEnhancer enhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInformation = new HashMap<>();
            String clientId = authentication.getOAuth2Request().getClientId();
            additionalInformation.put(SecurityConstants.CLIENT_ID, clientId);

            // ????????????????????????????????????
            if (StrUtil.equals(authentication.getOAuth2Request().getGrantType(), SecurityConstants.CLIENT_MODE)) {
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                return accessToken;
            }

            CocoUser details = (CocoUser) authentication.getUserAuthentication().getPrincipal();
            // ???????????????????????????????????????????????????
            BriefUser briefUser = new BriefUser(details.getUserId(), details.getUsername());
            additionalInformation.put(SecurityConstants.DETAILS_USER, briefUser);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
            return accessToken;
        };
    }
}
