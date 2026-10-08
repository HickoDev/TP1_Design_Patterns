package tn.insat.tp1.domain.product;

public class Food implements Product {
    private final String name;
    private final double price;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void display() {
        System.out.println("Food: " + name + " - " + price);
    }
}
