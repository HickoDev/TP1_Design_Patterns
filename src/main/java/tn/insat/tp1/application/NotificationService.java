package tn.insat.tp1.application;

import java.util.Objects;
import tn.insat.tp1.application.port.Notification;

public final class NotificationService {
    private final Notification strategy;

    public NotificationService(Notification strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy");
    }

    public void send(String message) {
        strategy.send(Objects.requireNonNull(message, "message"));
    }
}
