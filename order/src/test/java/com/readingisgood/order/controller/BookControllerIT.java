package com.readingisgood.order.controller;

import com.readingisgood.order.domain.dto.BookDTO;
import com.readingisgood.order.request.CreateBookRequest;
import com.readingisgood.order.request.UpdateBookRequest;
import com.readingisgood.order.response.AuthenticateResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookControllerIT extends ControllerITBase {

    private String token;
    private String bookId;

    @BeforeAll
    public void setUp() {
        String url = "/api/auth?username=admin&password=123456";
        ResponseEntity<AuthenticateResponse> response =
                restTemplate.exchange(
                        url, HttpMethod.GET, new HttpEntity<>(null), AuthenticateResponse.class);

        token = Objects.requireNonNull(response.getBody()).getToken();
    }

    @Test
    void testShouldCreateBook() {
        // given
        String url = "/api/v1/book";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        CreateBookRequest request =
                CreateBookRequest.builder()
                        .author("Test Author")
                        .price(BigDecimal.ONE)
                        .price(BigDecimal.TEN)
                        .title("Title")
                        .unitsInStock(5)
                        .build();

        // when
        ResponseEntity<BookDTO> response =
                restTemplate.exchange(
                        url, HttpMethod.POST, new HttpEntity<>(request, headers), BookDTO.class);

        // then
        assertNotNull(response.getBody());
        bookId = response.getBody().getId();
        assertNotNull(response.getBody().getId());
        assertEquals(request.getAuthor(), response.getBody().getAuthor());
        assertEquals(request.getTitle(), response.getBody().getTitle());
    }

    @Test
    void testShouldUpdateBook() {
        // given
        String url = "/api/v1/book/" + bookId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        UpdateBookRequest request = UpdateBookRequest.builder().unitsInStock(50).build();

        // when
        ResponseEntity<BookDTO> response =
                restTemplate.exchange(
                        url, HttpMethod.PATCH, new HttpEntity<>(request, headers), BookDTO.class);

        // then
        assertNotNull(response.getBody());
        assertEquals(50, response.getBody().getUnitsInStock());
    }
}
