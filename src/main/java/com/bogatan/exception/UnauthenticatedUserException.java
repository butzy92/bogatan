package com.bogatan.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by mbutan on 4/12/2017.
 */
public class UnauthenticatedUserException extends BogatanException {
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getMessage() {
        return "The user cannot be authenticated";
    }
}
