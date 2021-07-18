package com.readingisgood.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/** @author hakan.ozerden */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    private String id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    @Override
    public String toString() {
        return "CustomerDTO{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", surname='"
                + surname
                + '\''
                + ", phoneNumber='"
                + phoneNumber
                + '\''
                + ", email='"
                + email
                + '\''
                + '}';
    }
}
