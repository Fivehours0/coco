package com.cococloud.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;


@JsonSerialize(using = CocoOAuth2ExceptionSerializer.class)
public class BadRequestException  extends OAuth2Exception {
    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadRequestException(String msg) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "bad_request";
    }

    @Override
    public int getHttpErrorCode() {
        return 400;
    }
}