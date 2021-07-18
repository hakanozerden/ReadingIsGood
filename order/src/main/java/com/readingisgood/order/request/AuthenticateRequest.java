package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/** @author hakan.ozerden */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticateRequest {

    @NotEmpty
    @ApiParam("Username")
    private String username;

    @NotEmpty
    @ApiParam("Password")
    private String password;
}
