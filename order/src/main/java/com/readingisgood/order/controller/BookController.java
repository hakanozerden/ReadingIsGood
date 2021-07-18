package com.readingisgood.order.controller;

import com.readingisgood.order.constants.ApiEndpoints;
import com.readingisgood.order.domain.dto.BookDTO;
import com.readingisgood.order.request.CreateBookRequest;
import com.readingisgood.order.request.UpdateBookRequest;
import com.readingisgood.order.response.ResponseBuilder;
import com.readingisgood.order.service.book.BookCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/** @author hakan.ozerden */
@RestController
@RequestMapping(
        value = ApiEndpoints.BOOK_API,
        produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Api(value = "book-api")
@RequiredArgsConstructor
public class BookController {

    private final BookCommandService commandService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create book", notes = "Create new book.", nickname = "create")
    public ResponseEntity<BookDTO> create(@Valid @RequestBody CreateBookRequest request) {
        return ResponseBuilder.build(commandService.create(request));
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update book", notes = "Update book by id.", nickname = "update")
    public ResponseEntity<BookDTO> update(
            @NotNull @PathVariable("id") String id, @Valid @RequestBody UpdateBookRequest request) {
        return ResponseBuilder.build(commandService.update(id, request));
    }
}
