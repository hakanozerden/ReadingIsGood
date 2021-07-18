package com.readingisgood.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** @author hakan.ozerden */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private String id;

    private BookDTO book;

    private CustomerDTO customer;

    private Integer quantity;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "OrderDTO{"
                + "id='"
                + id
                + '\''
                + ", book="
                + book
                + ", customer="
                + customer
                + ", quantity="
                + quantity
                + ", amount="
                + amount
                + ", createdAt="
                + createdAt
                + '}';
    }
}
