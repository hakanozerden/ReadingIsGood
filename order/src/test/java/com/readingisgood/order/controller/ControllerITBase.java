package com.readingisgood.order.controller;

import com.readingisgood.order.base.IntegrationTestBase;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/** @author hakan.ozerden */
public abstract class ControllerITBase extends IntegrationTestBase {

    @Autowired protected TestRestTemplate restTemplate;

    @BeforeAll
    public void setUp() {
        restTemplate
                .getRestTemplate()
                .setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
}
