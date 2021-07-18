package com.readingisgood.order.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** @author hakan.ozerden */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PagedApiRequest {
    public static final PagedApiRequest NO_PAGING = new PagedApiRequest(-1, 0);

    @ApiParam("Default 0 (first page), -1 means no paging")
    private int page = 0;

    @ApiParam("Default 20, ignored when no paging")
    private int size = 20;

    @ApiParam("Property to sort on, default is service specific")
    private Sort sort;

    public PagedApiRequest(int page, int size) {
        this(page, size, null);
    }

    public boolean usePaging() {
        return page >= 0 && size > 0;
    }
}
