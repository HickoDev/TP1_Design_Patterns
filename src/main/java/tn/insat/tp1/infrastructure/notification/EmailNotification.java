package tn.insat.tp1.infrastructure.notification;

import tn.insat.tp1.application.port.Notification;

public final class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Email : " + message);
    }
}
