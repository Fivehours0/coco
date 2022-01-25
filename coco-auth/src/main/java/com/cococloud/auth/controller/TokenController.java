package com.cococloud.auth.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cococloud.common.constant.CacheConstants;
import com.cococloud.common.util.CommentResult;
import com.cococloud.log.event.EventPublisher;
import com.cococloud.upms.common.entity.SysOauthClientDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final RedisTokenStore tokenStore;

    private final RedisTemplate<String, Object> redisTemplate;

    private final EventPublisher publisher;

    @DeleteMapping("/logout")
    public CommentResult<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        if (StrUtil.isBlank(auth)) {
            return CommentResult.ok();
        }
        String tokenValue = StrUtil.removePrefix(auth, OAuth2AccessToken.BEARER_TYPE).trim();
        return removeToken(tokenValue);
    }


    /**
     * 删除token
     * @param token token
     */
    @DeleteMapping("/{token}")
    public CommentResult<Boolean> removeToken(@PathVariable("token") String token) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return CommentResult.ok();
        }

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);

        // 清空access token
        tokenStore.removeAccessToken(accessToken);

        // 清空 refresh token
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        tokenStore.removeRefreshToken(refreshToken);

        // 处理自定义退出事件，保存相关日志
        publisher.publishEvent(new LogoutSuccessEvent(auth2Authentication));
        return CommentResult.ok();
    }

    /**
     * 分页获取token
     */
    @PostMapping("/page")
    public CommentResult<Page> getPageToken(Page page) {
        String matchKey = CacheConstants.PROJECT_OAUTH_ACCESS + CacheConstants.AUTH_TO_ACCESS;
        long curr = page.getCurrent();
        long size = page.getSize();

        Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(matchKey + "*").build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });

        if (CollUtil.isEmpty(keys)) {
            return CommentResult.ok();
        }

        List<String> pageKeys = keys.stream().skip((curr - 1) * size).limit(size).collect(Collectors.toList());
        Page result = new Page(curr, size);
        result.setRecords(redisTemplate.opsForValue().multiGet(pageKeys));
        result.setTotal(pageKeys.size());
        return CommentResult.ok(result);
    }

}
