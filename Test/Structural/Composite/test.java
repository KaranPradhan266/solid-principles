import java.util.ArrayList;
import java.util.List;

interface Products{
    String getName();
    double getPrice();
    void display();
}

// Individual Product (Leaf - problematic direct usage)
class Product implements Products{
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display() {
        System.out.println("  - Product: " + name + ", Price: $" + price);
    }
}

// Product Bundle (Composite - problematic direct usage)
class ProductBundle implements Products {
    private String name;
    private List<Products> items; // Can contain Product or other ProductBundle

    public ProductBundle(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public void add(Products item) { // Accepts Product or ProductBundle
        items.add(item);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        double total = 0;
        for(Products item: items){
            total += item.getPrice();
        }
        return total;
    }

    public void display() {
        System.out.println("Bundle: " + name);
        for(Products item : items){
            item.display();
        }
    }
}

// Problematic Client - has to differentiate between Product and ProductBundle
class OrderCalculator {
    public double calculateTotal(List<Products> orderItems) {
        double total = 0;
        System.out.println("Calculating order total...");

        for(Products item: orderItems){
            total += item.getPrice();
            item.display();
        }

        return total;
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        // Individual products
        Product laptop = new Product("Laptop", 1200.00);
        Product mouse = new Product("Mouse", 25.00);
        Product keyboard = new Product("Keyboard", 75.00);

        // Create a "Gaming Setup" bundle
        ProductBundle gamingBundle = new ProductBundle("Gaming Setup");
        gamingBundle.add(laptop);
        gamingBundle.add(mouse);
        gamingBundle.add(new Product("Gaming Headset", 100.00));

        // Create an "Office Essentials" bundle
        ProductBundle officeBundle = new ProductBundle("Office Essentials");
        officeBundle.add(keyboard);
        officeBundle.add(new Product("Monitor", 300.00));

        // Add a bundle within another bundle
        ProductBundle ultimateBundle = new ProductBundle("Ultimate Workstation");
        ultimateBundle.add(gamingBundle);
        ultimateBundle.add(officeBundle);
        ultimateBundle.add(new Product("Webcam", 50.00));

        // Create an order list with mixed individual products and bundles
        List<Products> customerOrder = new ArrayList<>();
        customerOrder.add(new Product("Desk", 200.00));
        customerOrder.add(ultimateBundle);
        customerOrder.add(new Product("Chair", 150.00));

        OrderCalculator calculator = new OrderCalculator();
        double finalTotal = calculator.calculateTotal(customerOrder);

        System.out.println("\nFinal Order Total: $" + finalTotal);

        System.out.println("\nProblem: The OrderCalculator has to differentiate between Products and ProductBundles.");
        System.out.println("It uses 'instanceof' checks and duplicated logic to handle individual items vs. groups.");
        System.out.println("Adding a new type of 'item' (e.g., a 'Service') would require modifying OrderCalculator.");
    }
}
