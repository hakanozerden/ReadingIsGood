package com.readingisgood.order.response;

import org.springframework.data.domain.Page;

import javax.validation.Valid;
import java.util.List;

/** @author hakan.ozerden */
public class PagedListResultResponse<D> {

    public static <D> PagedListResultResponse<D> create(Page<D> results) {
        return new PagedListResultResponse<>(results);
    }

    @Valid private final List<D> resultList;
    private final PageData page;

    public PagedListResultResponse(@Valid Page<D> resultList) {
        this.resultList = resultList.getContent();
        this.page = pageableToPageData(resultList);
    }

    public PageData getPage() {
        return page;
    }

    public List<D> getResultList() {
        return resultList;
    }

    private PageData pageableToPageData(Page<D> page) {
        return new PageData(page.getNumber(), page.getTotalPages(), (int) page.getTotalElements());
    }
}
