package com.readingisgood.order.constants.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Error {
    ENTITY_NOT_FOUND("Entity with given id not found."),
    INSUFFICIENT_STOCK("Insufficient stock."),
    CUSTOMER_NOT_FOUND("Customer not found"),
    ORDER_NOT_FOUND("Customer not found"),
    BOOK_NOT_FOUND("Book not found"),
    SYSTEM_ERROR("Unexpected system error occurred"),
    INVALID_JWT_SIGNATURE("Invalid JWT signature"),
    INVALID_JWT("Invalid JWT"),
    JWT_EXPIRED("JWT expired"),
    JWT_NOT_SUPPORTED("JWT is unsupported"),
    UNAUTHORIZED("Unauthorized!"),
    VALIDATION_FAILED("Validation failed. Please check the username and password and try again."),
    INVALID_STOCK("Total stock can not be less than 0");

    private final String description;
}
