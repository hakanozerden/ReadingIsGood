package com.readingisgood.order.response;

import lombok.*;

/** @author hakan.ozerden */
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class AuthenticateResponse {

    private String token;

    private final String type = "Bearer";
}
