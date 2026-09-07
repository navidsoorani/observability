package org.example.paymentservice.controller;

import org.example.paymentservice.dto.PaymentRequest;
import org.example.paymentservice.dto.PaymentResponse;
import org.example.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService
    ) {

        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> payment(
            @RequestBody PaymentRequest request
    ) {

        PaymentResponse response =
                paymentService.processPayment(
                        request
                );

        return ResponseEntity.ok(response);
    }
}