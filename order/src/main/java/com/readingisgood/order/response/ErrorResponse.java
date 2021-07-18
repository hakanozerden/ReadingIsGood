package com.readingisgood.order.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** @author hakan.ozerden */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String errorDescription;

    public ErrorResponse(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
