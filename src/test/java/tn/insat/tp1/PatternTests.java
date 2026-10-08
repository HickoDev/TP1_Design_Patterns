package tn.insat.tp1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import tn.insat.tp1.application.NotificationService;
import tn.insat.tp1.application.OrderService;
import tn.insat.tp1.application.ProductFactory;
import tn.insat.tp1.application.port.Notification;
import tn.insat.tp1.domain.catalog.CatalogProduct;
import tn.insat.tp1.domain.catalog.Category;
import tn.insat.tp1.domain.order.Observer;
import tn.insat.tp1.domain.order.Order;
import tn.insat.tp1.domain.product.Book;
import tn.insat.tp1.domain.product.Clothing;
import tn.insat.tp1.domain.product.Electronic;
import tn.insat.tp1.domain.product.Food;
import tn.insat.tp1.domain.product.Product;
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

/** Tests executables sans bibliotheque externe ; tout echec produit un code de sortie non nul. */
public final class PatternTests {
    private static int passed;
    private static int failed;
    private static String selectedPart;

    private PatternTests() { }

    public static void main(String[] args) {
        selectedPart = args.length == 0 ? "all" : args[0];
        if (args.length > 1 || !List.of("all", "2", "3", "4", "5", "6", "7").contains(selectedPart)) {
            throw new IllegalArgumentException("Usage: PatternTests [all|2|3|4|5|6|7]. Parts 1 and 8: see README.md.");
        }
        passed = 0;
        failed = 0;
        test(2, "Factory creates all four product types", PatternTests::factoryTypes);
        test(2, "Factory rejects an unknown product type", PatternTests::factoryUnknownType);
        test(2, "OrderService supports Food", PatternTests::orderServiceFood);
        test(3, "Singleton shares identity and configuration", PatternTests::singleton);
        test(4, "Adapter delegates 250 exactly once", PatternTests::adapterDelegation);
        test(5, "Composite displays nested categories and products", PatternTests::composite);
        test(6, "Observer starts at CREATED and notifies the three services", PatternTests::observerServices);
        test(6, "Observer supports detach", PatternTests::observerDetach);
        test(6, "Observer preserves list subscriptions and notifies on every setStatus", PatternTests::observerEveryCall);
        test(7, "Strategy supports all four notification channels", PatternTests::strategyChannels);
        test(7, "Strategy accepts a new implementation without service changes", PatternTests::strategyExtension);
        System.out.println("\nTests: " + passed + " passed, " + failed + " failed.");
        if (failed != 0) { throw new AssertionError(failed + " test(s) failed"); }
    }

    private static void factoryTypes() {
        String[] types = {"BOOK", "ELECTRONIC", "CLOTHING", "FOOD"};
        Class<?>[] classes = {Book.class, Electronic.class, Clothing.class, Food.class};
        String[] labels = {"Book", "Electronic", "Clothing", "Food"};
        for (int i = 0; i < types.length; i++) {
            Product product = ProductFactory.createProduct(types[i], "Demo", 45);
            equal(classes[i], product.getClass());
            equal(labels[i] + ": Demo - 45.0\n", capture(product::display));
        }
    }

    private static void factoryUnknownType() {
        rejects(IllegalArgumentException.class,
                () -> ProductFactory.createProduct("UNKNOWN", "Demo", 1));
    }

    private static void orderServiceFood() {
        equal("Order created\nFood: Pasta - 12.0\n",
                capture(() -> new OrderService().createOrder("FOOD", "Pasta", 12)));
    }

    private static void singleton() {
        ApplicationConfig first = ApplicationConfig.getInstance();
        ApplicationConfig second = ApplicationConfig.getInstance();
        check(first == second, "getInstance must return the same object");
        String oldName = first.getApplicationName();
        String oldUrl = first.getDatabaseUrl();
        try {
            first.setApplicationName("Test Shop");
            first.setDatabaseUrl("jdbc:test:shop");
            equal("Test Shop", second.getApplicationName());
            equal("jdbc:test:shop", second.getDatabaseUrl());
        } finally {
            first.setApplicationName(oldName);
            first.setDatabaseUrl(oldUrl);
        }
    }

    private static final class RecordingPayment extends OldPaymentSystem {
        private int calls;
        private double amount;

        @Override
        public void makePayment(double amount) { this.amount = amount; calls++; }
    }

    private static void adapterDelegation() {
        RecordingPayment legacy = new RecordingPayment();
        new PaymentAdapter(legacy).pay(250);
        equal(1, legacy.calls);
        equal(250.0, legacy.amount);
        equal("Payment : 250.0\n", capture(() -> new PaymentAdapter(new OldPaymentSystem()).pay(250)));
    }

    private static void composite() {
        Category root = new Category("Catalogue");
        Category books = new Category("Books");
        Category programming = new Category("Programming");
        CatalogProduct book = new CatalogProduct("Design Patterns");
        programming.add(book);
        books.add(programming);
        root.add(books);
        root.add(new CatalogProduct("Pasta"));
        equal("Catalogue\n  Books\n    Programming\n      - Design Patterns\n  - Pasta\n",
                capture(() -> root.display("")));
    }

    private static void observerServices() {
        Order order = new Order();
        List<String> received = new ArrayList<>();
        order.attach(received::add);
        order.notifyObservers();
        equal(List.of("CREATED"), received);
        order.attach(new EmailService());
        order.attach(new StockService());
        order.attach(new LoggerService());
        equal("Email - status: SHIPPED\nStock - status: SHIPPED\nLogger - status: SHIPPED\n",
                capture(() -> order.setStatus("SHIPPED")));
        equal(List.of("CREATED", "SHIPPED"), received);
    }

    private static void observerDetach() {
        Order order = new Order();
        List<String> received = new ArrayList<>();
        List<String> remaining = new ArrayList<>();
        Observer observer = received::add;
        order.attach(observer);
        order.attach(remaining::add);
        order.setStatus("SHIPPED");
        equal(List.of("SHIPPED"), received);
        order.detach(observer);
        order.setStatus("DELIVERED");
        equal(List.of("SHIPPED"), received);
        equal(List.of("SHIPPED", "DELIVERED"), remaining);
    }

    private static void observerEveryCall() {
        Order order = new Order();
        List<String> received = new ArrayList<>();
        Observer observer = received::add;
        order.attach(observer);
        order.attach(observer);
        order.setStatus("SHIPPED");
        order.setStatus("SHIPPED");
        equal(List.of("SHIPPED", "SHIPPED", "SHIPPED", "SHIPPED"), received);
    }

    private static void strategyChannels() {
        List<Notification> strategies = List.of(new EmailNotification(), new SmsNotification(),
                new PushNotification(), new WhatsAppNotification());
        List<String> labels = List.of("Email", "SMS", "Push Notification", "WhatsApp");
        for (int i = 0; i < strategies.size(); i++) {
            NotificationService service = new NotificationService(strategies.get(i));
            equal("Sending " + labels.get(i) + " : Order shipped\n",
                    capture(() -> service.send("Order shipped")));
        }
    }

    private static void strategyExtension() {
        List<String> received = new ArrayList<>();
        NotificationService service = new NotificationService(received::add);
        service.send("Custom notification");
        equal(List.of("Custom notification"), received);
    }

    private static String capture(Runnable action) {
        PrintStream previous = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream output = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(output);
            action.run();
        } finally {
            System.setOut(previous);
        }
        return bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    private static void check(boolean condition, String message) {
        if (!condition) { throw new AssertionError(message); }
    }

    private static void equal(Object expected, Object actual) {
        check(Objects.equals(expected, actual), "Expected <" + expected + "> but got <" + actual + ">");
    }

    private static void rejects(Class<? extends Throwable> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) { return; }
            throw new AssertionError("Expected " + expected.getSimpleName() + " but got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }

    private static void test(int part, String name, Runnable action) {
        if (!selectedPart.equals("all") && !selectedPart.equals(Integer.toString(part))) { return; }
        try {
            action.run();
            passed++;
            System.out.println("[PASS] " + name);
        } catch (Throwable error) {
            failed++;
            System.out.println("[FAIL] " + name + ": " + error);
            error.printStackTrace(System.out);
        }
    }
}
