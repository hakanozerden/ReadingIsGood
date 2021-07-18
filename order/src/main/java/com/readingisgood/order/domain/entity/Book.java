package com.readingisgood.order.domain.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/** @author hakan.ozerden */
@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book extends AbstractEntity {

    @NotBlank private String title;

    @NotBlank private String author;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    private Integer unitsInStock;

    public boolean isSufficient(Integer quantity) {
        return this.unitsInStock.compareTo(quantity) >= 0;
    }
}
