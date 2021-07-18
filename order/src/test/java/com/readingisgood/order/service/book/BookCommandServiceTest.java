package com.readingisgood.order.service.book;

import com.readingisgood.order.domain.dto.BookDTO;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.domain.enums.OrderStatus;
import com.readingisgood.order.exception.BusinessException;
import com.readingisgood.order.exception.EntityNotFoundException;
import com.readingisgood.order.repository.BookRepository;
import com.readingisgood.order.request.CreateBookRequest;
import com.readingisgood.order.request.UpdateBookRequest;
import com.readingisgood.order.service.MutateService;
import com.readingisgood.order.utils.validator.StockValidator;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
class BookCommandServiceTest {

    @Mock private BookRepository mockRepository;
    @Mock private MutateService<Book> mockMutateService;

    private BookCommandService bookCommandServiceUnderTest;

    @BeforeEach
    void setUp() {
        bookCommandServiceUnderTest =
                new BookCommandService(
                        mockRepository, new ModelMapper(), mockMutateService, new StockValidator());
    }

    @Test
    void testShouldCreateBook() {
        // given
        final CreateBookRequest request =
                new CreateBookRequest("title", "author", new BigDecimal("0.00"), 1);
        final Book book = new Book("title", "author", new BigDecimal("0.00"), 1);
        when(mockRepository.save(Mockito.any())).thenReturn(book);

        // when
        final BookDTO result = bookCommandServiceUnderTest.create(request);

        // then
        assertNotNull(result, "Result is null.");
        assertEquals("Title is incorrect.", book.getTitle(), result.getTitle());
        assertEquals("Author is incorrect.", book.getAuthor(), result.getAuthor());
    }

    @Test
    void testShouldUpdateBook() {
        // given
        final UpdateBookRequest request = new UpdateBookRequest(1);

        final Optional<Book> book =
                Optional.of(new Book("title", "author", new BigDecimal("0.00"), 1));
        when(mockRepository.findById(Mockito.any())).thenReturn(book);

        final Book book2 = new Book("title", "author", new BigDecimal("0.00"), 0);
        when(mockMutateService.mutate(any(Book.class), any(Book.class))).thenReturn(book2);

        when(mockRepository.save(Mockito.any())).thenReturn(book.get());

        // when
        final BookDTO result = bookCommandServiceUnderTest.update("bookId", request);

        // then
        Mockito.verify(mockRepository, Mockito.times(1)).save(book2);
        assertNotNull(result, "not found.");
        assertEquals("Unit is incorrect.", 1, result.getUnitsInStock());
    }

    @Test
    void testShouldThrowException_BookRepositoryFindByIdReturnsAbsent() {
        // given
        final UpdateBookRequest request = new UpdateBookRequest(0);
        when(mockRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // when - then
        assertThrows(
                EntityNotFoundException.class,
                () -> bookCommandServiceUnderTest.update("bookId", request));
    }

    @Test
    void testHandleOrder() {
        // given
        final Optional<Book> book =
                Optional.of(new Book("title", "author", new BigDecimal("0.00"), 2));
        when(mockRepository.findById(Mockito.any())).thenReturn(book);

        final Book book1 = new Book("title", "author", new BigDecimal("0.00"), 1);
        when(mockRepository.save(any(Book.class))).thenReturn(book1);

        // when
        final Book result =
                bookCommandServiceUnderTest.handleOrder("bookId", 1, OrderStatus.ACTIVE);

        // then
        assertNotNull(result, "not found.");
        assertEquals("Unit is incorrect.", 1, result.getUnitsInStock());
    }

    @Test
    void testShouldThrowExceptionWhenHandleOrder_BookRepositoryFindByIdReturnsAbsent() {
        // given
        when(mockRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // when - then
        assertThrows(
                EntityNotFoundException.class,
                () -> bookCommandServiceUnderTest.handleOrder("bookId", 0, OrderStatus.ACTIVE));
    }

    @Test
    void testShouldThrowExceptionWhenHandleOrder_IfStockInsufficient() {
        // given
        final Optional<Book> book =
                Optional.of(new Book("title", "author", new BigDecimal("0.00"), 2));
        when(mockRepository.findById(Mockito.any())).thenReturn(book);

        // when - then
        assertThrows(
                BusinessException.class,
                () -> bookCommandServiceUnderTest.handleOrder("bookId", 3, OrderStatus.ACTIVE));
    }
}
