package com.readingisgood.order.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.*;

/** @author hakan.ozerden */
public class SwaggerConfigurer {

    private final String title;
    private final String description;
    private final String path;
    private final String contactName;
    private final String contactEmail;
    private final String contactUrl;
    private final String version;
    private final String group;

    public SwaggerConfigurer(
            String title,
            String description,
            String version,
            String contactName,
            String contactEmail,
            String contactUrl,
            String path,
            String group) {
        this.title = title;
        this.description = description;
        this.path = path;
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactUrl = contactUrl;
        this.version = version;
        this.group = group;
    }

    public Docket build() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(apis())
                .paths(apiPaths())
                .build()
                .groupName(group)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .globalResponses(HttpMethod.GET, response())
                .globalResponses(HttpMethod.POST, response())
                .globalResponses(HttpMethod.PUT, response())
                .globalResponses(HttpMethod.DELETE, response())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .description(description)
                .build();
    }

    private Predicate<String> apiPaths() {
        return PathSelectors.regex(path);
    }

    private Predicate<RequestHandler> apis() {
        return RequestHandlerSelectors.withClassAnnotation(RestController.class);
    }

    private List<Response> response() {
        return asList(
                new ResponseBuilder()
                        .code(String.valueOf(OK.value()))
                        .description("Request processed successfully")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(CREATED.value()))
                        .description("New resource created successfully")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(BAD_REQUEST.value()))
                        .description("Request parameters are invalid")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(INTERNAL_SERVER_ERROR.value()))
                        .description("Server was unable to process the request")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(NOT_FOUND.value()))
                        .description("Requested resource was not found")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(BAD_GATEWAY.value()))
                        .description("Downstream server returned unknown response")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(GATEWAY_TIMEOUT.value()))
                        .description("Downstream server did not return in time")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(SERVICE_UNAVAILABLE.value()))
                        .description("Downstream server is not available")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(FORBIDDEN.value()))
                        .description("Credentials are not enough to view requested resource")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(UNAUTHORIZED.value()))
                        .description("HTTP basic auth parameters are wrong or empty")
                        .build());
    }
}
