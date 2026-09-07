package org.example.orderpaymentobservability.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(

        String customerId,

        String product,

        int quantity,

        BigDecimal amount

) {
}