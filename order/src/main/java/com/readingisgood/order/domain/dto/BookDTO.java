package com.readingisgood.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/** @author hakan.ozerden */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDTO {

    private String id;

    private String title;

    private String author;

    private BigDecimal price;

    private Integer unitsInStock;

    @Override
    public String toString() {
        return "BookDTO{"
                + "id='"
                + id
                + '\''
                + ", title='"
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
