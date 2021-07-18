package com.readingisgood.order.service.order;

import com.readingisgood.order.constants.errors.Error;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/** @author hakan.ozerden */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository repository;

    private final ModelMapper mapper;

    private final BookCommandService bookCommandService;

    private final CustomerQueryService customerQueryService;

    /**
     * Create new order with given parameters
     *
     * @param request
     * @return
     */
    @Transactional
    public OrderDTO create(NewOrderRequest request) {

        log.debug("New Order : {} requested.", request);

        Optional<Customer> customer = customerQueryService.findById(request.getCustomerId());

        if (customer.isEmpty())
            throw new EntityNotFoundException(Error.CUSTOMER_NOT_FOUND.getDescription());

        Book book =
                bookCommandService.handleOrder(
                        request.getBookId(), request.getQuantity(), OrderStatus.ACTIVE);

        Order newOrder =
                Order.builder()
                        .amount(book.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                        .book(book)
                        .customer(customer.get())
                        .quantity(request.getQuantity())
                        .status(OrderStatus.ACTIVE)
                        .build();

        OrderDTO orderDTO = mapper.map(repository.save(newOrder), OrderDTO.class);

        log.debug("Order : {} successfully created.", orderDTO);
        return orderDTO;
    }
}
