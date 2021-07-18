package com.readingisgood.order.request;

import com.readingisgood.order.domain.enums.SortDirection;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/** @author hakan.ozerden */
@Getter
@Setter
public class Sort {
    @ApiParam("Field on the domain model to sort on")
    private String field;

    @ApiParam("Direction of sort operation, default ASC")
    private SortDirection direction;

    public Sort() {
        this(null, SortDirection.ASC);
    }

    public Sort(String field) {
        this(field, SortDirection.ASC);
    }

    public Sort(String field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

}
