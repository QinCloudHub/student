package com.management.app.api;

import com.management.app.infrastructure.exception.EntityNotFoundException;
import com.management.app.infrastructure.exception.PermissionException;
import com.management.app.support.Rest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Rest<Object> handlerException(SQLIntegrityConstraintViolationException e) {
        System.out.println(e.toString());
        return Rest.failure("执行sql语句时发生规约错误: " + e.getMessage());
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public Rest<?> handlerEntityNotFountException(EntityNotFoundException e) {
        System.out.println(e.toString());
        return Rest.failure(e.getMessage());
    }

    @ExceptionHandler(PermissionException.class)
    public Rest<?> handlerPermissionException(PermissionException e) {
        System.out.println(e.toString());
        return Rest.refused(e.getMessage());
    }


}
