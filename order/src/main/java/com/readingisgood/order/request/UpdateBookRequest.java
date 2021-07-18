package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/** @author hakan.ozerden */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateBookRequest {
    @NotNull
    @PositiveOrZero
    @ApiParam("New stock value of the book")
    private Integer unitsInStock;
}
