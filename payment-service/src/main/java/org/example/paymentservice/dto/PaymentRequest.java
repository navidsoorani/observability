package org.example.paymentservice.dto;

import java.math.BigDecimal;

public record PaymentRequest(

        String orderId,

        BigDecimal amount

) {
}