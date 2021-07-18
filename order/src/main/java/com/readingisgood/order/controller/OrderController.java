package com.readingisgood.order.controller;

import com.readingisgood.order.constants.ApiEndpoints;
import com.readingisgood.order.constants.errors.Error;
import com.readingisgood.order.domain.dto.OrderDTO;
import com.readingisgood.order.exception.EntityNotFoundException;
import com.readingisgood.order.request.NewOrderRequest;
import com.readingisgood.order.response.ResponseBuilder;
import com.readingisgood.order.service.order.OrderCommandService;
import com.readingisgood.order.service.order.OrderQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;

/** @author hakan.ozerden */
@RestController
@RequestMapping(
        value = ApiEndpoints.ORDER,
        produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Api(value = "order-api")
@Validated
@RequiredArgsConstructor
public class OrderController {

    private final OrderCommandService commandService;
    private final OrderQueryService orderQueryService;

    @PostMapping(
            value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create Order", notes = "Create new order.", nickname = "create")
    public ResponseEntity<OrderDTO> create(@Validated @RequestBody NewOrderRequest request) {
        return ResponseBuilder.build(commandService.create(request));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get Order", notes = "Get order by id.", nickname = "get")
    public ResponseEntity<OrderDTO> get(@NotNull @PathVariable("id") String id) {
        Optional<OrderDTO> order = orderQueryService.findById(id);
        return order.map(ResponseBuilder::build)
                .orElseThrow(
                        () -> new EntityNotFoundException(Error.ORDER_NOT_FOUND.getDescription()));
    }

    @GetMapping(value = "/search/{startDate}/{endDate}")
    @ApiOperation(
            value = "Search Orders",
            notes =
                    "List orders by date interval. startDate and endDate format must be yyyy-MM-dd!",
            nickname = "searchOrdersByDateInterval")
    public ResponseEntity<Collection<OrderDTO>> searchOrdersByDateInterval(
            @ApiParam(
                            value = "Start date of interval. Format must be yyyy-MM-dd",
                            name = "startDate",
                            type = "String",
                            required = true)
                    @NotNull
                    @PathVariable("startDate")
                    String startDate,
            @ApiParam(
                            value = "End date of interval. Format must be yyyy-MM-dd",
                            name = "endDate",
                            type = "String",
                            required = true)
                    @NotNull
                    @PathVariable("endDate")
                    String endDate) {
        return ResponseBuilder.build(
                orderQueryService.searchOrdersByDateInterval(startDate, endDate));
    }
}
