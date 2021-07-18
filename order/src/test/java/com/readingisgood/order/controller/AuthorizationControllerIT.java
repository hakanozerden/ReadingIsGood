package com.readingisgood.order.controller;

import com.readingisgood.order.response.AuthenticateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthorizationControllerIT extends ControllerITBase {

    @Test
    void testAuthenticate() {
        // given
        String url = "/api/auth?username=admin&password=123456";

        // when
        ResponseEntity<AuthenticateResponse> response =
                restTemplate.exchange(
                        url, HttpMethod.GET, new HttpEntity<>(null), AuthenticateResponse.class);

        // then
        assertNotNull(response, "Response is null.");
        assertNotNull(Objects.requireNonNull(response.getBody()).getToken(), "Token is null.");
    }
}
