package com.oipithesecond.glboot;

public class OrderService {
    public void placeOrder() {
        var paymentService = new StripePaymentService();
        paymentService.processPayment(100);
    }
}
