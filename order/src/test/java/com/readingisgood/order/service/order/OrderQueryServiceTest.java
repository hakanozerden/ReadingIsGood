package com.readingisgood.order.service.order;

import com.readingisgood.order.domain.dto.OrderDTO;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.domain.entity.Order;
import com.readingisgood.order.domain.enums.OrderStatus;
import com.readingisgood.order.repository.OrderRepository;
import com.readingisgood.order.request.PagedApiRequest;
import com.readingisgood.order.response.PagedListResultResponse;
import com.readingisgood.order.response.StatisticResponse;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    @Mock private OrderRepository mockRepository;
    @Mock private MongoTemplate mockTemplate;

    private OrderQueryService orderQueryServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderQueryServiceUnderTest =
                new OrderQueryService(mockRepository, new ModelMapper(), mockTemplate);
    }

    @Test
    void testFindByIdShouldReturnOrder() {
        // given
        final Optional<Order> order =
                Optional.of(
                        new Order(
                                new Book("title", "author", new BigDecimal("0.00"), 0),
                                new Customer("name", "surname", "phoneNumber", "email"),
                                1,
                                new BigDecimal("0.00"),
                                OrderStatus.ACTIVE));
        when(mockRepository.findById(Mockito.any())).thenReturn(order);

        // when
        final Optional<OrderDTO> result = orderQueryServiceUnderTest.findById("id");

        // then
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getQuantity());
    }

    @Test
    void testFindById_OrderRepositoryReturnsAbsent() {
        // ´given
        when(mockRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // when
        final Optional<OrderDTO> result = orderQueryServiceUnderTest.findById("id");

        // then
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllShouldReturnAllOrders() {
        // given
        final List<Order> orders =
                List.of(
                        new Order(
                                new Book("title", "author", new BigDecimal("0.00"), 0),
                                new Customer("name", "surname", "phoneNumber", "email"),
                                0,
                                new BigDecimal("0.00"),
                                OrderStatus.ACTIVE));
        when(mockRepository.findAll()).thenReturn(orders);
        // when
        final Collection<OrderDTO> result = orderQueryServiceUnderTest.findAll();

        // then
        assertEquals(1, result.size());
    }

    @Test
    void testListOrdersOfCustomerShouldReturnOrdersOfCustomer() {
        // given
        final PagedApiRequest pagedApiRequest = new PagedApiRequest(0, 0);
        final Page<Order> orders =
                new PageImpl<>(
                        List.of(
                                new Order(
                                        new Book("title", "author", new BigDecimal("0.00"), 0),
                                        new Customer("name", "surname", "phoneNumber", "email"),
                                        0,
                                        new BigDecimal("0.00"),
                                        OrderStatus.ACTIVE)));
        when(mockRepository.findAllByCustomer_Id(eq("customerId"), any(Pageable.class)))
                .thenReturn(orders);

        // Run the test
        final PagedListResultResponse<OrderDTO> result =
                orderQueryServiceUnderTest.listOrdersOfCustomer("customerId", pagedApiRequest);

        // then
        assertEquals(1, result.getResultList().size());
        assertEquals(1, result.getPage().getTotalPages());
    }

    @Test
    void testSearchOrdersByDateInterval() {
        // given
        final Collection<Order> orders =
                List.of(
                        new Order(
                                new Book("title", "author", new BigDecimal("0.00"), 0),
                                new Customer("name", "surname", "phoneNumber", "email"),
                                0,
                                new BigDecimal("0.00"),
                                OrderStatus.ACTIVE));
        when(mockRepository.findAllByCreatedAtBetween(
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 0, 0, 0)))
                .thenReturn(orders);

        // Run the test
        final Collection<OrderDTO> result =
                orderQueryServiceUnderTest.searchOrdersByDateInterval("2020-01-01", "2021-01-01");

        // then
        assertEquals(1, result.size());
    }

    @Test
    void testSearchOrdersByDateInterval_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockRepository.findAllByCreatedAtBetween(
                        LocalDateTime.of(2019, 1, 1, 0, 0, 0),
                        LocalDateTime.of(2019, 1, 1, 0, 0, 0)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final Collection<OrderDTO> result =
                orderQueryServiceUnderTest.searchOrdersByDateInterval("2019-01-01", "2019-01-01");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetCustomersMonthlyOrderStatistics() {
        // given
        final AggregationResults<StatisticResponse> statisticResponses =
                new AggregationResults<>(
                        List.of(new StatisticResponse("id", 0, 0, new BigDecimal("0.00"), "month")),
                        new Document("key", "value"));
        when(mockTemplate.aggregate(
                        any(Aggregation.class), eq(Order.class), eq(StatisticResponse.class)))
                .thenReturn(statisticResponses);

        // when
        final List<StatisticResponse> result =
                orderQueryServiceUnderTest.getCustomersMonthlyOrderStatistics();

        // then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
