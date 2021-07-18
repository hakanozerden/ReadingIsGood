package com.readingisgood.order.service.order;

import com.readingisgood.order.domain.dto.OrderDTO;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.domain.entity.Order;
import com.readingisgood.order.domain.enums.OrderStatus;
import com.readingisgood.order.exception.EntityNotFoundException;
import com.readingisgood.order.repository.OrderRepository;
import com.readingisgood.order.request.NewOrderRequest;
import com.readingisgood.order.service.book.BookCommandService;
import com.readingisgood.order.service.customer.CustomerQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

    @Mock private OrderRepository mockRepository;
    @Mock private BookCommandService mockBookCommandService;
    @Mock private CustomerQueryService mockCustomerQueryService;

    private OrderCommandService orderCommandServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderCommandServiceUnderTest =
                new OrderCommandService(
                        mockRepository,
                        new ModelMapper(),
                        mockBookCommandService,
                        mockCustomerQueryService);
    }

    @Test
    void testCreate() {
        // given
        final NewOrderRequest request = new NewOrderRequest("bookId", "customerId", 0);

        final Optional<Customer> customer =
                Optional.of(new Customer("name", "surname", "phoneNumber", "email"));
        when(mockCustomerQueryService.findById(Mockito.any())).thenReturn(customer);

        final Book book = new Book("title", "author", new BigDecimal("0.00"), 0);
        when(mockBookCommandService.handleOrder("bookId", 0, OrderStatus.ACTIVE)).thenReturn(book);

        final Order order =
                new Order(
                        new Book("title", "author", new BigDecimal("0.00"), 0),
                        new Customer("name", "surname", "phoneNumber", "email"),
                        1,
                        new BigDecimal("0.00"),
                        OrderStatus.ACTIVE);
        when(mockRepository.save(any(Order.class))).thenReturn(order);

        // when
        final OrderDTO result = orderCommandServiceUnderTest.create(request);

        // then
        assertNotNull(result, "Result is null.");
        assertEquals("Quantity is incorrect.", order.getQuantity(), result.getQuantity());
    }

    @Test
    void testCreateShouldThrowException_CustomerQueryServiceReturnsAbsent() {
        // given
        final NewOrderRequest request = new NewOrderRequest("bookId", "customerId", 0);
        when(mockCustomerQueryService.findById(Mockito.any())).thenReturn(Optional.empty());

        // when - then
        assertThrows(
                EntityNotFoundException.class, () -> orderCommandServiceUnderTest.create(request));
    }
}
