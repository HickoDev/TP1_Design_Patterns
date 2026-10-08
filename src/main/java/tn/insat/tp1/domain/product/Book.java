package tn.insat.tp1.domain.product;

public class Book implements Product {
    private final String name;
    private final double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void display() {
        System.out.println("Book: " + name + " - " + price);
    }
}
