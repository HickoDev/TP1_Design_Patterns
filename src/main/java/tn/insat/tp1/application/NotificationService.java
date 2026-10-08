package tn.insat.tp1.application;

import tn.insat.tp1.application.port.Notification;

public class NotificationService {
    private final Notification strategy;

    public NotificationService(Notification strategy) {
        this.strategy = strategy;
    }

    public void send(String message) {
        strategy.send(message);
    }
}
