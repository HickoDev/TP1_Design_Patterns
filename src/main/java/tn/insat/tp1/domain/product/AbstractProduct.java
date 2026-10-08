package tn.insat.tp1.domain.product;

/** Donnees communes aux produits ; les prix restent en double comme dans le TP. */
public abstract class AbstractProduct implements Product {
    private final String label;
    private final String name;
    private final double price;

    protected AbstractProduct(String label, String name, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be blank");
        }
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException("Price must be finite and non-negative");
        }
        this.label = label;
        this.name = name;
        this.price = price;
    }

    @Override
    public final String getName() { return name; }

    @Override
    public final double getPrice() { return price; }

    @Override
    public void display() {
        System.out.println(label + ": " + name + " - " + price);
    }
}
