package com.readingisgood.order.exception;

/** @author hakan.ozerden */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
