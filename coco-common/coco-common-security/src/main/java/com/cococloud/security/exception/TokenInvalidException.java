package com.cococloud.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 令牌失效
 * @author pkl
 */
@JsonSerialize(using = CocoOAuth2ExceptionSerializer.class)
public class TokenInvalidException extends OAuth2Exception {

    public TokenInvalidException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_token";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FAILED_DEPENDENCY.value();
    }

}

