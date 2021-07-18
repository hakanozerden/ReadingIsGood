package com.readingisgood.order.domain.entity;

import com.readingisgood.order.domain.enums.OrderStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

/** @author hakan.ozerden */
@Document(collection = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order extends AbstractEntity {

    @DBRef private Book book;

    @DBRef private Customer customer;

    private Integer quantity;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal amount;

    private OrderStatus status;
}
