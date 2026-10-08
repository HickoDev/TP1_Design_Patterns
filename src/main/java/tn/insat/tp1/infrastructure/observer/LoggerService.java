package tn.insat.tp1.infrastructure.observer;

import tn.insat.tp1.domain.order.Observer;

public final class LoggerService implements Observer {
    @Override
    public void update(String status) {
        System.out.println("Logger - status: " + status);
    }
}
