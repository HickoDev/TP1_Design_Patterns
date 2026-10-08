package tn.insat.tp1.infrastructure.notification;

import tn.insat.tp1.application.port.Notification;

public final class SmsNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS : " + message);
    }
}
