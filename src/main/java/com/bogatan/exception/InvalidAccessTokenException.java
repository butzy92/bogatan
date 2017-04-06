package com.bogatan.exception;

import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends BogatanException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return "Invalid access token";
    }
}
