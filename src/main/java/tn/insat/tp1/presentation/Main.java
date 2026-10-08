package tn.insat.tp1.presentation;

import java.util.List;
import tn.insat.tp1.application.NotificationService;
import tn.insat.tp1.application.OrderService;
import tn.insat.tp1.application.port.Notification;
import tn.insat.tp1.application.port.PaymentService;
import tn.insat.tp1.domain.catalog.CatalogProduct;
import tn.insat.tp1.domain.catalog.Category;
import tn.insat.tp1.domain.order.Order;
import tn.insat.tp1.infrastructure.config.ApplicationConfig;
import tn.insat.tp1.infrastructure.notification.EmailNotification;
import tn.insat.tp1.infrastructure.notification.PushNotification;
import tn.insat.tp1.infrastructure.notification.SmsNotification;
import tn.insat.tp1.infrastructure.notification.WhatsAppNotification;
import tn.insat.tp1.infrastructure.observer.EmailService;
import tn.insat.tp1.infrastructure.observer.LoggerService;
import tn.insat.tp1.infrastructure.observer.StockService;
import tn.insat.tp1.infrastructure.payment.OldPaymentSystem;
import tn.insat.tp1.infrastructure.payment.PaymentAdapter;

/** Point de composition : branche les abstractions et les implementations. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        String selected = args.length == 0 ? "all" : args[0];
        if (args.length > 1 || !List.of("all", "2", "3", "4", "5", "6", "7").contains(selected)) {
            throw new IllegalArgumentException("Usage: Main [all|2|3|4|5|6|7]. Parts 1 and 8: see README.md.");
        }
        System.out.println("TP1 - Design Patterns | Ali Dridi - Rayenne Abid | RT5");
        List<Runnable> demos = List.of(Main::factory, Main::singleton, Main::adapter,
                Main::composite, Main::observer, Main::strategy);
        for (int index = 0; index < demos.size(); index++) {
            if (selected.equals("all") || selected.equals(Integer.toString(index + 2))) {
                demos.get(index).run();
            }
        }
    }

    private static void factory() {
        System.out.println("\nPartie 2 - FACTORY");
        OrderService orders = new OrderService();
        orders.createOrder("BOOK", "Design Patterns", 45);
        orders.createOrder("ELECTRONIC", "Laptop", 2500);
        orders.createOrder("CLOTHING", "T-shirt", 30);
        orders.createOrder("FOOD", "Pasta", 12);
    }

    private static void singleton() {
        System.out.println("\nPartie 3 - SINGLETON");
        ApplicationConfig c1 = ApplicationConfig.getInstance();
        ApplicationConfig c2 = ApplicationConfig.getInstance();
        System.out.println(c1 == c2);
    }

    private static void adapter() {
        System.out.println("\nPartie 4 - ADAPTER");
        PaymentService payment = new PaymentAdapter(new OldPaymentSystem());
        payment.pay(250);
    }

    private static void composite() {
        System.out.println("\nPartie 5 - COMPOSITE");
        Category catalogue = new Category("Catalogue");
        Category books = new Category("Books");
        books.add(new CatalogProduct("Book 1"));
        books.add(new CatalogProduct("Book 2"));
        Category electronics = new Category("Electronics");
        electronics.add(new CatalogProduct("Laptop"));
        electronics.add(new CatalogProduct("Smartphone"));
        catalogue.add(books);
        catalogue.add(electronics);
        catalogue.display("");
    }

    private static void observer() {
        System.out.println("\nPartie 6 - OBSERVER");
        Order order = new Order();
        order.attach(new EmailService());
        order.attach(new StockService());
        order.attach(new LoggerService());
        order.setStatus("SHIPPED");
    }

    private static void strategy() {
        System.out.println("\nPartie 7 - STRATEGY");
        List<Notification> channels = List.of(new EmailNotification(), new SmsNotification(),
                new PushNotification(), new WhatsAppNotification());
        for (Notification channel : channels) {
            new NotificationService(channel).send("Order shipped");
        }
    }
}
