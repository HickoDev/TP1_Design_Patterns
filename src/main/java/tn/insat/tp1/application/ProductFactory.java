package tn.insat.tp1.application;

import java.util.Locale;
import tn.insat.tp1.domain.product.Book;
import tn.insat.tp1.domain.product.Clothing;
import tn.insat.tp1.domain.product.Electronic;
import tn.insat.tp1.domain.product.Food;
import tn.insat.tp1.domain.product.Product;

/** Simple Factory : le choix des classes concretes est centralise ici. */
public final class ProductFactory {
    private ProductFactory() { }

    public static Product createProduct(String type, String name, double price) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Product type must not be blank");
        }
        return switch (type.trim().toUpperCase(Locale.ROOT)) {
            case "BOOK" -> new Book(name, price);
            case "ELECTRONIC" -> new Electronic(name, price);
            case "CLOTHING" -> new Clothing(name, price);
            case "FOOD" -> new Food(name, price);
            default -> throw new IllegalArgumentException("Unknown product: " + type);
        };
    }
}
