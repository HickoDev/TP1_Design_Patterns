package tn.insat.tp1.domain.product;

public class Electronic implements Product {
    private final String name;
    private final double price;

    public Electronic(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void display() {
        System.out.println("Electronic: " + name + " - " + price);
    }
}
