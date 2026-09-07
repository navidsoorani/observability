package org.example.orderpaymentobservability.dto;

import java.math.BigDecimal;

public record PaymentRequest(

        String orderId,

        BigDecimal amount

) {
}