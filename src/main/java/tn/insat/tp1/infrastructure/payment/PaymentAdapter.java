package tn.insat.tp1.infrastructure.payment;

import java.util.Objects;
import tn.insat.tp1.application.port.PaymentService;

public final class PaymentAdapter implements PaymentService {
    private final OldPaymentSystem oldPaymentSystem;

    public PaymentAdapter(OldPaymentSystem oldPaymentSystem) {
        this.oldPaymentSystem = Objects.requireNonNull(oldPaymentSystem, "oldPaymentSystem");
    }

    @Override
    public void pay(double amount) {
        if (!Double.isFinite(amount) || amount < 0) {
            throw new IllegalArgumentException("Amount must be finite and non-negative");
        }
        oldPaymentSystem.makePayment(amount);
    }
}
