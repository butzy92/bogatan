package com.bogatan.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionTranslator {


    @ExceptionHandler(BogatanException.class)
    public void processBogatanException(BogatanException e, HttpServletResponse servletResponse) {
        try {
            servletResponse.sendError(e.getStatus().value(), e.getMessage());
        } catch (IOException e1) {
           /*asdasdas*/
        }
    }


    @ExceptionHandler(SQLException.class)
    public void processSQLException(SQLException e, HttpServletResponse servletResponse) {
        try {
            servletResponse.sendError(500, e.getMessage());
        } catch (IOException e1) {
           /*asdasdas*/
        }
    }
}
