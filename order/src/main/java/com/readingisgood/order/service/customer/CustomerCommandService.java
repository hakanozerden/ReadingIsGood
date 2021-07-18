package com.readingisgood.order.service.customer;

import com.readingisgood.order.domain.dto.CustomerDTO;
import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.repository.CustomerRepository;
import com.readingisgood.order.request.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCommandService {

    private final CustomerRepository repository;

    private final ModelMapper mapper;

    public CustomerDTO insert(CreateCustomerRequest request) {

        log.debug("Insert new Customer : {}.", request);

        CustomerDTO customerDTO =
                Optional.of(mapper.map(request, Customer.class))
                        .map(repository::save)
                        .map(e -> mapper.map(e, CustomerDTO.class))
                        .orElse(null);
        log.debug("Customer : {} successfully created.", customerDTO);
        return customerDTO;
    }
}
