package tn.insat.tp1.domain.product;

public class Clothing implements Product {
    private final String name;
    private final double price;

    public Clothing(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void display() {
        System.out.println("Clothing: " + name + " - " + price);
    }
}
