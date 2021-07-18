package com.readingisgood.order.domain.entity;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/** @author hakan.ozerden */
@Document(collection  = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer extends AbstractEntity {

    private String name;

    private String surname;

    @Indexed(unique = true)
    private String phoneNumber;

    @Indexed(unique = true)
    private String email;

}
