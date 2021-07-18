package com.readingisgood.order.repository;

import com.readingisgood.order.domain.entity.Order;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;

/** @author hakan.ozerden */
@JaversSpringDataAuditable
public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllByCustomer_Id(String customerId, Pageable pageRequest);

    Collection<Order> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
