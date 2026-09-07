package org.example.orderpaymentobservability.service;


import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import org.example.orderpaymentobservability.dto.CreateOrderRequest;
import org.example.orderpaymentobservability.dto.OrderResponse;
import org.example.orderpaymentobservability.dto.PaymentRequest;
import org.example.orderpaymentobservability.dto.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class OrderService {

    private final RestClient paymentRestClient;

    private final ObservationRegistry observationRegistry;

    public OrderService(
            RestClient paymentRestClient,
            ObservationRegistry observationRegistry
    ) {

        this.paymentRestClient = paymentRestClient;
        this.observationRegistry = observationRegistry;
    }

    public OrderResponse createOrder(
            CreateOrderRequest request
    ) {

        return Observation
                .createNotStarted(
                        "order.create",
                        observationRegistry
                )
                .lowCardinalityKeyValue(
                        "order.operation",
                        "create"
                )
                .observe(() -> {

                    System.out.println(
                            "Creating order..."
                    );

                    String orderId =
                            "ORD-" + UUID.randomUUID();

                    /*
                     * Call Payment Service
                     */
                    PaymentResponse paymentResponse =
                            paymentRestClient
                                    .post()
                                    .uri("/api/payments")
                                    .body(
                                            new PaymentRequest(
                                                    orderId,
                                                    request.amount()
                                            )
                                    )
                                    .retrieve()
                                    .body(
                                            PaymentResponse.class
                                    );

                    /*
                     * Return order
                     */
                    return new OrderResponse(

                            orderId,

                            request.customerId(),

                            request.product(),

                            request.quantity(),

                            request.amount(),

                            paymentResponse.status(),

                            paymentResponse.paymentId()
                    );
                });
    }
}