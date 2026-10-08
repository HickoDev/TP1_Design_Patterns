package tn.insat.tp1.application;

import tn.insat.tp1.domain.product.Book;
import tn.insat.tp1.domain.product.Clothing;
import tn.insat.tp1.domain.product.Electronic;
import tn.insat.tp1.domain.product.Food;
import tn.insat.tp1.domain.product.Product;

/** Simple Factory : le choix des classes concretes est centralise ici. */
public class ProductFactory {
    public static Product createProduct(String type, String name, double price) {
        return switch (type.toUpperCase()) {
            case "BOOK" -> new Book(name, price);
            case "ELECTRONIC" -> new Electronic(name, price);
            case "CLOTHING" -> new Clothing(name, price);
            case "FOOD" -> new Food(name, price);
            default -> throw new IllegalArgumentException("Unknown product");
        };
    }
}
