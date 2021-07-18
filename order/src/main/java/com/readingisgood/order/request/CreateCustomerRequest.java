package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/** @author hakan.ozerden */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateCustomerRequest {

    @NotEmpty
    @Length(max = 150)
    @ApiParam("Name of the customer")
    private String name;

    @NotEmpty
    @Length(max = 150)
    @ApiParam("Surname of the customer")
    private String surname;

    @NotEmpty
    @Length(min = 10, max = 13)
    @ApiParam("Phone number of the customer")
    private String phoneNumber;

    @NotEmpty @Email
    @ApiParam("Email of the customer")
    private String email;

    @Override
    public String toString() {
        return "CreateCustomerRequest{"
                + "name='"
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
