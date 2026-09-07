package org.example.paymentservice.service;


import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import org.example.paymentservice.dto.PaymentRequest;
import org.example.paymentservice.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final ObservationRegistry observationRegistry;

    public PaymentService(
            ObservationRegistry observationRegistry
    ) {

        this.observationRegistry =
                observationRegistry;
    }

    public PaymentResponse processPayment(
            PaymentRequest request
    ) {

        return Observation
                .createNotStarted(
                        "payment.process",
                        observationRegistry
                )
                .lowCardinalityKeyValue(
                        "payment.operation",
                        "process"
                )
                .observe(() -> {

                    System.out.println(
                            "Processing payment for order: "
                                    + request.orderId()
                    );

                    /*
                     * Demo failure.
                     *
                     * Send amount 13.37 to see
                     * an error in Zipkin.
                     */
                    if (
                            request.amount()
                                    .doubleValue()
                                    == 13.37
                    ) {

                        throw new IllegalStateException(
                                "Payment failed intentionally"
                        );
                    }

                    /*
                     * Pretend that we called
                     * a real payment provider.
                     */
                    try {

                        Thread.sleep(200);

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();

                        throw new IllegalStateException(
                                "Payment interrupted",
                                e
                        );
                    }

                    String paymentId =
                            "PAY-" + UUID.randomUUID();

                    return new PaymentResponse(
                            paymentId,
                            "PAID"
                    );
                });
    }
}