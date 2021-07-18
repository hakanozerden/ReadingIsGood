package com.readingisgood.order.utils;

import com.readingisgood.order.request.PagedApiRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** @author hakan.ozerden */
public class Utils {

    public static Pageable pageRequestToPageable(PagedApiRequest apiRequest) {
        return apiRequest.usePaging()
                ? PageRequest.of(
                        apiRequest.getPage(), apiRequest.getSize(), apiSortToDataSort(apiRequest))
                : Pageable.unpaged();
    }

    private static Sort apiSortToDataSort(PagedApiRequest apiRequest) {
        if (apiRequest.getSort() != null) {
            return Sort.by(
                    Sort.Direction.valueOf(apiRequest.getSort().getDirection().name()),
                    apiRequest.getSort().getField());
        } else {
            return Sort.by(Sort.Direction.DESC, "id");
        }
    }
}
