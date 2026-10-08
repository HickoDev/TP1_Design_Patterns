package tn.insat.tp1.infrastructure.payment;

import tn.insat.tp1.application.port.PaymentService;

public class PaymentAdapter implements PaymentService {
    private final OldPaymentSystem oldPaymentSystem;

    public PaymentAdapter(OldPaymentSystem oldPaymentSystem) {
        this.oldPaymentSystem = oldPaymentSystem;
    }

    @Override
    public void pay(double amount) {
        oldPaymentSystem.makePayment(amount);
    }
}
