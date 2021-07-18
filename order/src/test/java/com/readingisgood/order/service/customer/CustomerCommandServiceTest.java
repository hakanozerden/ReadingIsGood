package com.readingisgood.order.service.customer;

import com.readingisgood.order.domain.dto.BookDTO;
import com.readingisgood.order.domain.dto.CustomerDTO;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.domain.entity.Customer;
import com.readingisgood.order.repository.CustomerRepository;
import com.readingisgood.order.request.CreateCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerCommandServiceTest {

    @Mock private CustomerRepository mockRepository;

    private CustomerCommandService customerCommandServiceUnderTest;

    @BeforeEach
    void setUp() {

        customerCommandServiceUnderTest =
                new CustomerCommandService(mockRepository, new ModelMapper());
    }

    @Test
    void testShouldCreateCustomer() {
        // Setup
        final CreateCustomerRequest request =
                new CreateCustomerRequest("name", "surname", "phoneNumber", "email");

        final Customer customer = new Customer("name", "surname", "phoneNumber", "email");
        when(mockRepository.save(Mockito.any())).thenReturn(customer);

        // when
        final CustomerDTO result = customerCommandServiceUnderTest.insert(request);

        // then
        assertNotNull(result, "Result is null.");
        assertEquals("Name is incorrect.", customer.getName(), result.getName());
        assertEquals("Surname is incorrect.", customer.getSurname(), result.getSurname());
    }
}
