package com.management.app.infrastructure.exception;

/**
 * 未找到指定实体异常
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
