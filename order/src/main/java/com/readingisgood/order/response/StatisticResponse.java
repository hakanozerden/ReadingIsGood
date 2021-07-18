package com.readingisgood.order.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/** @author hakan.ozerden */
@Builder
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class StatisticResponse {

    @JsonIgnore
    private String id;

    private Integer totalOrderCount;

    private Integer totalBookCount;

    private BigDecimal totalPurchasedAmount;

    private String month;

    public String getMonth() {
        return !ObjectUtils.isEmpty(id)
                ? Month.of(Integer.parseInt(id))
                        .getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)
                : "";
    }
}
