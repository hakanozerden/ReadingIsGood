package com.readingisgood.order.controller;

import com.readingisgood.order.constants.ApiEndpoints;
import com.readingisgood.order.request.AuthenticateRequest;
import com.readingisgood.order.response.AuthenticateResponse;
import com.readingisgood.order.response.ResponseBuilder;
import com.readingisgood.order.service.authentication.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/** @author hakan.ozerden */
@RestController
@RequestMapping(
        value = ApiEndpoints.AUTHORIZATION,
        produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Api(value = "authorization-api")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthenticationService authenticationService;

    @GetMapping(value = "")
    @ApiOperation(
            value = "Authenticate user",
            notes =
                    "Authenticate user with given username and password credentials. If user successfull authenticated, JWT token will generated.",
            nickname = "authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@Valid AuthenticateRequest request) {
        return ResponseBuilder.build(authenticationService.authenticateUser(request));
    }
}
