package com.cococloud.common.constant;

public interface SecurityConstants {

    String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * 之所以与{noop}合并，因为在{@link org.springframework.security.crypto.password.DelegatingPasswordEncoder}的matches方法
     * 中，String id = extractId(prefixEncodedPassword)会对加密方式进行提取
     */
    String CLIENT_FIELDS = "CONCAT('{noop}',client_secret), " + CLIENT_FIELDS_FOR_UPDATE;

    /**
     * 修改了表的名称
     */
    String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS + " from sys_oauth_client_details";

    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    /**
     * 角色信息前缀
     */
    String ROLE_PREFIX = "ROLE_";

    /**
     * 密码前缀
     */
    String POSSWORD_ENCODING = "{bcrypt}";

    /**
     * 请求token的端点
     */
    String REQUEST_TOKEN = "/oauth/token";

    /**
     * 请求token的端点
     */
    String REFRESH_TOKEN = "refresh_token";
}
