package com.readingisgood.order.service.book;

import com.readingisgood.order.domain.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class BookMutateServiceImplTest {

    private BookMutateServiceImpl bookMutateServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        bookMutateServiceImplUnderTest = new BookMutateServiceImpl();
    }

    @Test
    void testShouldMutateEntity() {
        // given
        final Book current = new Book("title", "author", new BigDecimal("0.00"), 0);
        final Book changes = Book.builder().title("title2").build();

        // when
        final Book result = bookMutateServiceImplUnderTest.mutate(current, changes);

        // then
        assertNotNull(result, "not found.");
        assertEquals("Title is incorrect.", changes.getTitle(), result.getTitle());
        assertEquals("Author is incorrect.", current.getAuthor(), result.getAuthor());
    }
}
