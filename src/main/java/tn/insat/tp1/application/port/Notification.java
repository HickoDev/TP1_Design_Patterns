package tn.insat.tp1.application.port;

@FunctionalInterface
public interface Notification {
    void send(String message);
}
