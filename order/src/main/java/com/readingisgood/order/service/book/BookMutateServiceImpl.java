package com.readingisgood.order.service.book;

import com.readingisgood.order.domain.entity.Book;
import com.readingisgood.order.service.MutateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/** @author hakan.ozerden */
@Service("bookMutateService")
@RequiredArgsConstructor
public class BookMutateServiceImpl implements MutateService<Book> {

    @Override
    public Book mutate(Book current, Book changes) {

        if (!ObjectUtils.isEmpty(changes.getAuthor())) current.setAuthor(changes.getAuthor());

        if (!ObjectUtils.isEmpty(changes.getPrice())) current.setPrice(changes.getPrice());

        if (!ObjectUtils.isEmpty(changes.getUnitsInStock()))
            current.setUnitsInStock(changes.getUnitsInStock());

        if (!ObjectUtils.isEmpty(changes.getTitle())) current.setTitle(changes.getTitle());

        return current;
    }
}
