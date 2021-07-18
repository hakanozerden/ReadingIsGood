package com.readingisgood.order.controller;

import com.readingisgood.order.constants.ApiEndpoints;
import com.readingisgood.order.response.ResponseBuilder;
import com.readingisgood.order.response.StatisticResponse;
import com.readingisgood.order.service.order.OrderQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** @author hakan.ozerden */
@RestController
@RequestMapping(value = ApiEndpoints.STATISTIC)
@Api(value = "statistic-api")
@Validated
@RequiredArgsConstructor
public class StatisticsController {

    private final OrderQueryService orderQueryService;

    @GetMapping()
    @ApiOperation(
            value = "",
            notes = "Get customer's monthly order statistics.",
            nickname = "getCustomersMontlyOrderStatistics")
    public ResponseEntity<List<StatisticResponse>> getCustomersMontlyOrderStatistics() {
        return ResponseBuilder.build(orderQueryService.getCustomersMonthlyOrderStatistics());
    }
}
