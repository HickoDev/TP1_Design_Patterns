package tn.insat.tp1.domain.order;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Sujet synchrone ; ne depend d'aucun service concret. */
public final class Order implements Subject {
    private String status = "CREATED";
    private final Set<Observer> observers = new LinkedHashSet<>();

    public String getStatus() { return status; }

    @Override
    public void attach(Observer observer) {
        observers.add(Objects.requireNonNull(observer, "observer"));
    }

    @Override
    public void detach(Observer observer) { observers.remove(observer); }

    @Override
    public void notifyObservers() {
        String currentStatus = status;
        // Un observateur peut se desabonner pendant son propre rappel.
        for (Observer observer : List.copyOf(observers)) {
            observer.update(currentStatus);
        }
    }

    public void setStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Order status must not be blank");
        }
        if (!this.status.equals(status)) {
            this.status = status;
            notifyObservers();
        }
    }
}
