package com.bogatan.exception;

import org.springframework.http.HttpStatus;

public class AccessTokenNotFoundException extends BogatanException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return "Authorization header not found";
    }
}

