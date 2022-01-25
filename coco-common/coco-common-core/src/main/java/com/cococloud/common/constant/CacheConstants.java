package com.cococloud.common.constant;

public interface CacheConstants {
    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY:";

    /**
     * oauth access token 缓存前缀
     */
    String PROJECT_OAUTH_ACCESS = "coco_oauth:access:";

    /**
     * RedisTokenStore存储的access_token前缀
     * 实际为PROJECT_OAUTH_ACCESS + AUTH_TO_ACCESS
     */
    String AUTH_TO_ACCESS = "auth_to_access:";
}
