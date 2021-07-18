package com.readingisgood.order.controller;

import com.readingisgood.order.constants.ApiEndpoints;
import com.readingisgood.order.domain.dto.CustomerDTO;
import com.readingisgood.order.domain.dto.OrderDTO;
import com.readingisgood.order.request.CreateCustomerRequest;
import com.readingisgood.order.request.PagedApiRequest;
import com.readingisgood.order.response.PagedListResultResponse;
import com.readingisgood.order.response.ResponseBuilder;
import com.readingisgood.order.service.customer.CustomerCommandService;
import com.readingisgood.order.service.order.OrderQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/** @author hakan.ozerden */
@RestController
@RequestMapping(value = ApiEndpoints.CUSTOMER_API, produces = { ApiEndpoints.RESPONSE_CONTENT_TYPE })
@Api(value = "customer-api")
@Validated
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerCommandService commandService;
    private final OrderQueryService orderQueryService;

    @PostMapping(
            value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create customer", notes = "Create new customer.", nickname = "create")
    public ResponseEntity<CustomerDTO> create(
            @Validated @RequestBody CreateCustomerRequest request) {
        return ResponseBuilder.build(commandService.insert(request));
    }

    @GetMapping(value = "/orders/{customerId}")
    @ApiOperation(
            value = "Get orders of customer.",
            notes = "Get orders of customer.",
            nickname = "getOrdersOfCustomer")
    public PagedListResultResponse<OrderDTO> getOrdersOfCustomer(
            @NotNull @PathVariable("customerId") String customerId,
            PagedApiRequest pagedApiRequest) {
        return orderQueryService.listOrdersOfCustomer(customerId, pagedApiRequest);
    }
}
