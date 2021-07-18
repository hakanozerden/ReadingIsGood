package com.readingisgood.order.exception;

/** @author hakan.ozerden */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
