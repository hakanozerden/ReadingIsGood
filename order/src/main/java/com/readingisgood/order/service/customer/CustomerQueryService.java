package com.readingisgood.order.service.customer;

import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.repository.CustomerRepository;
import com.readingisgood.order.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerQueryService implements QueryService<Customer> {

    private final CustomerRepository repository;

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Customer> findAll() {
        return repository.findAll();
    }
}
