interface PaymentProcessor{
    void processPayments(double amount, String currency);
    boolean isPaymentSuccessful();
    String getTransactionId();
}

class InhousePaymentProcessor implements PaymentProcessor{
    private String transactionId;
    private boolean isPaymentSuccessful;

    @Override
    public void processPayments(double amount, String currency){
        System.out.println("InHousePaymentProcessor: Processing payment of " + amount + " " + currency);
        isPaymentSuccessful = true;
        System.out.println("InHousePaymentProcessor: Payment successful. Txn ID: " + transactionId);
    }

    @Override
    public boolean isPaymentSuccessful(){
        return isPaymentSuccessful;
    }

    @Override
    public String getTransactionId(){
        return transactionId;
    }
}

class CheckoutService {
    private PaymentProcessor paymentProcessor;

    public CheckoutService(PaymentProcessor paymentProcessor){
        this.paymentProcessor = paymentProcessor;
    }

    public void checkout(double amount, String currency){
        System.out.println("CheckoutService: Attempting to process order for $" + amount + " " + currency);
        paymentProcessor.processPayments(amount, currency);
        if (paymentProcessor.isPaymentSuccessful()) {
            System.out.println("CheckoutService: Order successful! Transaction ID: " 
                               + paymentProcessor.getTransactionId());
        } else {
            System.out.println("CheckoutService: Order failed. Payment was not successful.");
        }
    }
}

class LegacyGateway {
    private long transactionReference;
    private boolean isPaymentSuccessful;

    public void executeTransaction(double totalAmount, String currency) {
        System.out.println("LegacyGateway: Executing transaction for " 
                           + currency + " " + totalAmount);
        transactionReference = System.nanoTime();
        isPaymentSuccessful = true;
        System.out.println("LegacyGateway: Transaction executed successfully. Txn ID: " 
                           + transactionReference);
    }

    public boolean checkStatus(long transactionReference) {
        System.out.println("LegacyGateway: Checking status for ref: " + transactionReference);
        return isPaymentSuccessful;
    }

    public long getReferenceNumber() {
        return transactionReference;
    }
}

class LegacyGatewayAdapter implements PaymentProcessor{
    private final LegacyGateway legacyGateway;
    private long currentRef;

    public LegacyGatewayAdapter(LegacyGateway legacyGateway){
        this.legacyGateway = legacyGateway;
    }

    @Override
    public void processPayments(double amount, String currency){
        System.out.println("Adapter: Translating processPayment() for " + amount + " " + currency);
        legacyGateway.executeTransaction(amount, currency);
        currentRef = legacyGateway.getReferenceNumber();
    }

    @Override
    public boolean isPaymentSuccessful(){
        return legacyGateway.checkStatus(currentRef);
    }

    @Override
    public String getTransactionId(){
        return "LEGAGY_TXN_"+ currentRef;
    }
}

public class Adapter {
    public static void main(String[] args) {
        PaymentProcessor paymentProcessor = new InhousePaymentProcessor();
        CheckoutService checkout = new CheckoutService(paymentProcessor);

        checkout.checkout(1000, "USD");
        
        System.out.println("\n--- Using Legacy Gateway via Adapter ---");
        LegacyGateway legacy = new LegacyGateway();
        paymentProcessor = new LegacyGatewayAdapter(legacy);
        CheckoutService legacyCheckout = new CheckoutService(paymentProcessor);
        legacyCheckout.checkout(75.50, "USD");
    }
}