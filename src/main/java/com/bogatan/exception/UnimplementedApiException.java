package com.bogatan.exception;

import org.springframework.http.HttpStatus;

public class UnimplementedApiException extends BogatanException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getMessage() {
        return "Unimplemented API";
    }
}
