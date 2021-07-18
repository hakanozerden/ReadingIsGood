package com.readingisgood.order.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/** @author hakan.ozerden */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseBuilder {

    public static ResponseEntity<ErrorResponse> build(
            ErrorResponse errorResponse, HttpStatus status) {
        return new ResponseEntity<>(errorResponse, status);
    }

    public static <T> ResponseEntity<T> build(T item) {
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
