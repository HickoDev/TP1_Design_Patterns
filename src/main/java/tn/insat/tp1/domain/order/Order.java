package tn.insat.tp1.domain.order;

import java.util.ArrayList;
import java.util.List;

/** Sujet synchrone ; ne depend d'aucun service concret. */
public class Order implements Subject {
    private String status = "CREATED";
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) { observers.remove(observer); }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }
}
