package com.management.app.infrastructure.exception;

/**
 * 权限相关异常
 */
public class PermissionException extends RuntimeException {

    public PermissionException(String message) {
        super(message);
    }
}
