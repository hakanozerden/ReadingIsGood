package com.readingisgood.order.base;

import com.readingisgood.order.OrderApplication;
import org.springframework.boot.test.context.SpringBootTest;

/** @author hakan.ozerden */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OrderApplication.class)
public abstract class IntegrationTestBase {}
