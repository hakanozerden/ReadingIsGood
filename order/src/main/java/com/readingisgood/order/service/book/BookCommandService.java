package com.readingisgood.order.service.book;

import com.readingisgood.order.constants.errors.Error;
import com.readingisgood.order.domain.dto.BookDTO;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.domain.enums.OrderStatus;
import com.readingisgood.order.exception.EntityNotFoundException;
import com.readingisgood.order.repository.BookRepository;
import com.readingisgood.order.request.CreateBookRequest;
import com.readingisgood.order.request.UpdateBookRequest;
import com.readingisgood.order.service.MutateService;
import com.readingisgood.order.utils.validator.StockValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** @author hakan.ozerden */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookCommandService {

    private final BookRepository repository;

    private final ModelMapper mapper;

    @Qualifier("bookMutateService")
    private final MutateService<Book> mutateService;

    private final StockValidator stockValidator;

    /**
     * Create book with given parameters
     *
     * @param request
     * @return
     */
    public BookDTO create(CreateBookRequest request) {

        log.debug("New Book : {} requested.", request);

        BookDTO bookDTO =
                Optional.of(mapper.map(request, Book.class))
                        .map(repository::save)
                        .map(e -> mapper.map(e, BookDTO.class))
                        .orElse(null);
        log.debug("Book : {} successfully inserted.", bookDTO);
        return bookDTO;
    }

    /**
     * Update book.
     *
     * @param bookId
     * @param request
     * @return
     */
    public BookDTO update(String bookId, UpdateBookRequest request) {

        log.debug("Updating Book with Id : {}, Detail : {}.", bookId, request);

        Optional<Book> book = repository.findById(bookId);

        if (book.isEmpty())
            throw new EntityNotFoundException(Error.BOOK_NOT_FOUND.getDescription());

        BookDTO bookDTO =
                Optional.of(mapper.map(request, Book.class))
                        .map(b -> mutateService.mutate(book.get(), b))
                        .map(repository::save)
                        .map(b -> mapper.map(b, BookDTO.class))
                        .orElse(null);
        log.debug("Book : {} successfully updated.", bookDTO);
        return bookDTO;
    }

    public Book handleOrder(String bookId, Integer quantity, OrderStatus status) {
        Optional<Book> book = repository.findById(bookId);

        if (book.isEmpty())
            throw new EntityNotFoundException(Error.BOOK_NOT_FOUND.getDescription());

        if (OrderStatus.ACTIVE.equals(status)) {
            // validate
            stockValidator.validate(book.get(), quantity);

            book.get().setUnitsInStock(book.get().getUnitsInStock() - quantity);
        } else {
            book.get().setUnitsInStock(book.get().getUnitsInStock() + quantity);
        }
        return repository.save(book.get());
    }
}
