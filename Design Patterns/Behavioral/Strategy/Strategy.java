// 1. Strategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// 2. Concrete Strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String name;

    public CreditCardPayment(String cardNumber, String name) {
        this.cardNumber = cardNumber;
        this.name = name;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paying $" + amount + " using Credit Card (Card: " + cardNumber + ", Name: " + name + ")");
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paying $" + amount + " using PayPal (Email: " + email + ")");
    }
}

class BitcoinPayment implements PaymentStrategy {
    private String walletAddress;

    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paying $" + amount + " using Bitcoin (Wallet: " + walletAddress + ")");
    }
}

// 3. Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        if (paymentStrategy == null) {
            System.out.println("No payment strategy set. Cannot checkout.");
            return;
        }
        System.out.println("\nShopping Cart: Processing checkout for $" + amount);
        paymentStrategy.pay(amount);
        System.out.println("Shopping Cart: Checkout complete.");
    }
}

// Main class to demonstrate the Strategy pattern
public class Strategy {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        // Use Credit Card payment
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456", "John Doe"));
        cart.checkout(100.00);

        // Switch to PayPal payment at runtime
        cart.setPaymentStrategy(new PayPalPayment("john.doe@example.com"));
        cart.checkout(50.00);

        // Switch to Bitcoin payment
        cart.setPaymentStrategy(new BitcoinPayment("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        cart.checkout(25.00);

        // Try to checkout without a strategy
        ShoppingCart emptyCart = new ShoppingCart();
        emptyCart.checkout(10.00);
    }
}
