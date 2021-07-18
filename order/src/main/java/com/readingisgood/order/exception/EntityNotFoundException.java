package com.readingisgood.order.exception;

/** @author hakan.ozerden */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
