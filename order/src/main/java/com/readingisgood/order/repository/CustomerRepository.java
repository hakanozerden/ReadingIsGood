package com.readingisgood.order.repository;

import com.readingisgood.order.domain.entity.Customer;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

/** @author hakan.ozerden */
@JaversSpringDataAuditable
public interface CustomerRepository extends MongoRepository<Customer, String> {}
