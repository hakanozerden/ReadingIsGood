package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/** @author hakan.ozerden */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewOrderRequest {

    @NotEmpty
    @ApiParam("Id of the requested book")
    private String bookId;

    @NotEmpty
    @ApiParam("Requesting customer id")
    private String customerId;

    @NotNull @Positive
    @ApiParam("Quantity of book to be ordered")
    private Integer quantity;

    @Override
    public String toString() {
        return "NewOrderRequest{"
                + "bookId="
                + bookId
                + ", customerId="
                + customerId
                + ", quantity="
                + quantity
                + '}';
    }
}
