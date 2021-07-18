package com.readingisgood.order.service.order;

import com.readingisgood.order.domain.dto.OrderDTO;
import com.readingisgood.order.domain.entity.Order;
import com.readingisgood.order.repository.OrderRepository;
import com.readingisgood.order.request.PagedApiRequest;
import com.readingisgood.order.response.PagedListResultResponse;
import com.readingisgood.order.response.StatisticResponse;
import com.readingisgood.order.service.QueryService;
import com.readingisgood.order.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderQueryService implements QueryService<OrderDTO> {

    private final OrderRepository repository;

    private final ModelMapper mapper;

    private final MongoTemplate template;

    @Override
    public Optional<OrderDTO> findById(String id) {
        return repository.findById(id).map(o -> mapper.map(o, OrderDTO.class));
    }

    @Override
    public Collection<OrderDTO> findAll() {
        return repository.findAll().stream()
                .map(o -> mapper.map(o, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public PagedListResultResponse<OrderDTO> listOrdersOfCustomer(
            String customerId, PagedApiRequest pagedApiRequest) {
        Page<OrderDTO> orders =
                repository
                        .findAllByCustomer_Id(
                                customerId, Utils.pageRequestToPageable(pagedApiRequest))
                        .map(o -> mapper.map(o, OrderDTO.class));

        log.debug(
                "Number of orders : {} for customer : {}.", orders.getTotalElements(), customerId);
        return PagedListResultResponse.create(orders);
    }

    public Collection<OrderDTO> searchOrdersByDateInterval(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return repository
                .findAllByCreatedAtBetween(
                        LocalDate.parse(startDate, formatter).atStartOfDay(),
                        LocalDate.parse(endDate, formatter).atStartOfDay())
                .stream()
                .map(o -> mapper.map(o, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public List<StatisticResponse> getCustomersMonthlyOrderStatistics() {

        GroupOperation groupBy =
                group("month")
                        .sum("quantity")
                        .as("totalBookCount")
                        .sum("amount")
                        .as("totalPurchasedAmount")
                        .count()
                        .as("totalOrderCount");

        Aggregation agg =
                newAggregation(
                        project()
                                .and("createdAt")
                                .extractMonth()
                                .as("month")
                                .and("quantity")
                                .as("quantity")
                                .and("amount")
                                .as("amount"),
                        groupBy,
                        sort(Sort.Direction.ASC, "month"));

        AggregationResults<StatisticResponse> groupResults =
                template.aggregate(agg, Order.class, StatisticResponse.class);

        return groupResults.getMappedResults();
    }
}
