package com.readingisgood.order.exception;

/** @author hakan.ozerden */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
