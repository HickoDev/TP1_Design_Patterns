package tn.insat.tp1.application;

import tn.insat.tp1.domain.product.Product;

public class OrderService {
    public void createOrder(String type, String name, double price) {
        Product product = ProductFactory.createProduct(type, name, price);
        System.out.println("Order created");
        product.display();
    }
}
