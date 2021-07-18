package com.readingisgood.order.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/** @author hakan.ozerden */
@Configuration
@ConditionalOnProperty(
        prefix = "swagger",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket orderApi() {
        return new SwaggerConfigurer(
                        "ReadingIsGood",
                        "Online book retail webservice.",
                        "v1",
                        "Hakan Özerden",
                        "hakanozerden@yahoo.com",
                        "https://hakanozerden.com",
                        "/api/v1.*",
                        "order-api")
                .build();
    }

    @Bean
    public Docket authenticationApi() {
        return new SwaggerConfigurer(
                        "ReadingIsGood",
                        "Authentication api part of the online book retail webservice.",
                        "v1",
                        "Hakan Özerden",
                        "hakanozerden@yahoo.com",
                        "https://hakanozerden.com",
                        "/api/auth*",
                        "auth-api")
                .build();
    }
}
