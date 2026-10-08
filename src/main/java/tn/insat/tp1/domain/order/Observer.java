package tn.insat.tp1.domain.order;

@FunctionalInterface
public interface Observer {
    void update(String status);
}
