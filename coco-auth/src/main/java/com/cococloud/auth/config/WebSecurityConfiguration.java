package com.cococloud.auth.config;

import com.cococloud.auth.handler.CocoWebResponseExceptionTranslater;
import com.cococloud.security.component.IgnoreUrlProperties;
import com.cococloud.security.service.CocoUserDetailsServiceImpl;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.constant.SecurityConstants;
import com.cococloud.upms.common.feign.RemoteUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private final RemoteUserDetail remoteUserDetail;

    private final CacheManager cacheManager;

    private final IgnoreUrlProperties ignoreUrlProperties;

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        return jdbcClientDetailsService;
    }

    @Bean
    public RedisTokenStore redisTokenStore(RedisConnectionFactory connectionFactory) {
        RedisTokenStore redisTokenStore = new RedisTokenStore(connectionFactory);
        redisTokenStore.setPrefix(CacheConstants.PROJECT_OAUTH_ACCESS);
        return redisTokenStore;
    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CocoUserDetailsServiceImpl(remoteUserDetail, cacheManager);
    }

    @Bean
    public CocoWebResponseExceptionTranslater cocoWebResponseExceptionTranslater() {
        return new CocoWebResponseExceptionTranslater();
    }


    /**
     * only when using the resource owner password flow needs an AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    /**
//     * Override this method to configure {@link WebSecurity}. For example, if you wish to
//     * ignore certain requests.
//     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//    }
//
    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * Any endpoint that requires defense against common vulnerabilities can be specified
     * here, including public ones. See {@link HttpSecurity#authorizeRequests} and the
     * `permitAll()` authorization rule for more details on public endpoints.
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.authorizeRequests();
        List<String> urls = ignoreUrlProperties.getUrls();
        urls.forEach(url -> registry.antMatchers(url).permitAll());

        http.authorizeRequests().antMatchers("/token/logout", "/actuator/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
    }
}
