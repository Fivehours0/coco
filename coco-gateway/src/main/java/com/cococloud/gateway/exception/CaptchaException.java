package com.cococloud.gateway.exception;

public class CaptchaException extends RuntimeException {
    public CaptchaException() {
    }

    public CaptchaException(String message) {
        super(message);
    }
}