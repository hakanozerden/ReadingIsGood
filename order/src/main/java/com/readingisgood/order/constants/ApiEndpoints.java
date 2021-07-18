package com.readingisgood.order.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

/** @author hakan.ozerden */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiEndpoints {

    public static final String API_BASE_URL = "/api/v1";
    public static final String AUTH_BASE = "/api";
    public static final String CUSTOMER_API = API_BASE_URL + "/customer";
    public static final String BOOK_API = API_BASE_URL + "/book";
    public static final String ORDER = API_BASE_URL + "/order";
    public static final String STATISTIC = API_BASE_URL + "/statistic";
    public static final String AUTHORIZATION = AUTH_BASE + "/auth";
    public static final String RESPONSE_CONTENT_TYPE =
            MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";
}
