package com.readingisgood.order.utils.validator;

import com.readingisgood.order.constants.errors.Error;
import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class StockValidator {

    public void validate(Book book, Integer quantity) {
        if (!book.isSufficient(quantity)) {
            throw new BusinessException(Error.INSUFFICIENT_STOCK.getDescription());
        }
    }
}
