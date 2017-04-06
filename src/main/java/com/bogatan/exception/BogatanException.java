package com.bogatan.exception;

import org.springframework.http.HttpStatus;

public abstract class BogatanException extends Exception {

    public abstract HttpStatus getStatus();

    public abstract String getMessage();
}
