package tn.insat.tp1.infrastructure.observer;

import tn.insat.tp1.domain.order.Observer;

public final class EmailService implements Observer {
    @Override
    public void update(String status) {
        System.out.println("Email - status: " + status);
    }
}
