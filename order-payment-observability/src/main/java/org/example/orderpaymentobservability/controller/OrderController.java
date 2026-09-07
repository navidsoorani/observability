package org.example.orderpaymentobservability.controller;

import org.example.orderpaymentobservability.dto.CreateOrderRequest;
import org.example.orderpaymentobservability.dto.OrderResponse;
import org.example.orderpaymentobservability.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService
    ) {

        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody CreateOrderRequest request
    ) {

        OrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity.ok(response);
    }
}