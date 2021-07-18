package com.readingisgood.order.controller;

import com.readingisgood.order.domain.dto.CustomerDTO;
import com.readingisgood.order.request.CreateCustomerRequest;
import com.readingisgood.order.response.AuthenticateResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerIT extends ControllerITBase {

    private String token;

    @BeforeAll
    public void setUp() {
        String url = "/api/auth?username=admin&password=123456";
        ResponseEntity<AuthenticateResponse> response =
                restTemplate.exchange(
                        url, HttpMethod.GET, new HttpEntity<>(null), AuthenticateResponse.class);

        token = Objects.requireNonNull(response.getBody()).getToken();
    }

    @Test
    void testShouldCreateCustomer() {
        // given
        String url = "/api/v1/customer";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        CreateCustomerRequest request =
                CreateCustomerRequest.builder()
                        .name("Name")
                        .surname("Surname")
                        .email("test@test.com")
                        .phoneNumber("905551234665")
                        .build();

        // when
        ResponseEntity<CustomerDTO> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(request, headers),
                        CustomerDTO.class);

        // then
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(request.getName(), response.getBody().getName());
        assertEquals(request.getSurname(), response.getBody().getSurname());
    }
}
