package com.readingisgood.order.service.customer;

import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerQueryServiceTest {

    @Mock private CustomerRepository mockRepository;

    private CustomerQueryService customerQueryServiceUnderTest;

    @BeforeEach
    void setUp() {
        customerQueryServiceUnderTest = new CustomerQueryService(mockRepository);
    }

    @Test
    void testShouldFindCustomerById() {
        // given
        final Optional<Customer> customer =
                Optional.of(new Customer("name", "surname", "phoneNumber", "email"));
        when(mockRepository.findById(Mockito.any())).thenReturn(customer);

        // when
        final Optional<Customer> result = customerQueryServiceUnderTest.findById("id");

        // then
        assertTrue(result.isPresent());
        assertEquals("Name is incorrect.", customer.get().getName(), result.get().getName());
        assertEquals(
                "Surname is incorrect.", customer.get().getSurname(), result.get().getSurname());
    }

    @Test
    void testShouldFalseFindById_CustomerRepositoryReturnsAbsent() {
        // given
        when(mockRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // when
        final Optional<Customer> result = customerQueryServiceUnderTest.findById("id");

        // then
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAllShouldReturnAllCustomers() {
        // given
        final List<Customer> customers =
                List.of(new Customer("name", "surname", "phoneNumber", "email"));
        when(mockRepository.findAll()).thenReturn(customers);

        // when
        final Collection<Customer> result = customerQueryServiceUnderTest.findAll();

        // then
        assertEquals("Size is incorrect.", 1, result.size());
    }
}
