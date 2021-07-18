package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/** @author hakan.ozerden */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateBookRequest {

    @NotEmpty
    @Length(max = 300)
    @ApiParam("Title of the book")
    private String title;

    @NotEmpty
    @Length(max = 150)
    @ApiParam("Book author")
    private String author;

    @NotNull @Positive
    @ApiParam("Book prce")
    private BigDecimal price;

    @NotNull @Positive
    @ApiParam("Units in stock")
    private Integer unitsInStock;

    @Override
    public String toString() {
        return "CreateBookRequest{"
                + "title='"
                + title
                + '\''
                + ", author='"
                + author
                + '\''
                + ", price="
                + price
                + ", unitsInStock="
                + unitsInStock
                + '}';
    }
}
