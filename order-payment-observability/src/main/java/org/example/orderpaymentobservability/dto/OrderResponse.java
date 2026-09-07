package org.example.orderpaymentobservability.dto;

import java.math.BigDecimal;

public record OrderResponse(

        String orderId,

        String customerId,

        String product,

        int quantity,

        BigDecimal amount,

        String status,

        String paymentId

) {
}