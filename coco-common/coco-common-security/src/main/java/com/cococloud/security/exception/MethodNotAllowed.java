package com.cococloud.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CocoOAuth2ExceptionSerializer.class)
public class MethodNotAllowed extends OAuth2Exception {

    public MethodNotAllowed(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return 405;
    }
}
