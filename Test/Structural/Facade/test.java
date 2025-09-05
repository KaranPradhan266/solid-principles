// Subsystem Components
class InventoryService {
    public boolean checkStock(String productId, int quantity) {
        System.out.println("InventoryService: Checking stock for " + productId + ", quantity " + quantity);
        return true; // Assume stock is always available for this example
    }

    public void updateStock(String productId, int quantity) {
        System.out.println("InventoryService: Updating stock for " + productId + ", quantity " + quantity);
    }
}

class PaymentGateway {
    public String processPayment(double amount, String cardNumber, String expiryDate, String cvv) {
        System.out.println("PaymentGateway: Processing payment of $" + amount + " with card " + cardNumber);
        return "TXN" + System.currentTimeMillis(); // Simulate a transaction ID
    }
}

class ShippingService {
    public String createShipment(String address, String orderDetails) {
        System.out.println("ShippingService: Creating shipment to " + address + " for order: " + orderDetails);
        return "SHIP" + System.nanoTime(); // Simulate a tracking ID
    }

    public void trackShipment(String trackingId) {
        System.out.println("ShippingService: Tracking shipment " + trackingId);
    }
}

class NotificationService {
    public void sendEmail(String to, String subject, String body) {
        System.out.println("NotificationService: Sending email to " + to + " with subject: " + subject);
    }

    public void sendSms(String phoneNumber, String message) {
        System.out.println("NotificationService: Sending SMS to " + phoneNumber + ": " + message);
    }
}

class Order {
    private String productId;
    private int quantity;
    private double amount;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String shippingAddress;
    private String customerEmail;
    private String customerPhone;

    private Order(OrderBuilder builder){
        this.amount = builder.amount;
        this.cardNumber = builder.cardNumber;
        this.customerEmail= builder.customerEmail;
        this.customerPhone = builder.customerPhone;
        this.cvv = builder.cvv;
        this.expiryDate = builder.expiryDate;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.shippingAddress = builder.shippingAddress;
    }

    public String getProductId(){
        return productId;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getAmount(){
        return amount;
    }
    public String getCardNumber(){
        return cardNumber;
    }
    public String getExpiryDate(){
        return expiryDate;
    }
    public String getCvv(){
        return cvv;
    }
    public String getShippingAddress(){
        return shippingAddress;
    }
    public String getCustomerEmail(){
        return customerEmail;
    }
    public String getCustomerPhone(){
        return customerPhone;
    }

    public static class OrderBuilder{
        private String productId;
        private int quantity;
        private double amount;
        private String cardNumber;
        private String expiryDate;
        private String cvv;
        private String shippingAddress;
        private String customerEmail;
        private String customerPhone;

        public OrderBuilder(String productId, int quantity, double amount){
            this.productId = productId;
            this.quantity = quantity;
            this.amount = amount;
        }

        public OrderBuilder withPaymentDetails(String cardNumber, String expiryDate, String cvv){

            this.cardNumber = cardNumber;
            this.expiryDate = expiryDate;
            this.cvv = cvv;
            return this;
        }


        public OrderBuilder toAddress(String shippingAddress){
            this.shippingAddress = shippingAddress;
            return this;
        }

        public OrderBuilder forCustomer(String customerEmail, String customerPhone){
            this.customerEmail = customerEmail;
            this.customerPhone = customerPhone;
            return this;
        }

        public Order build(){
            return new Order(this);
        }
    }
}

class OrderFacade {
    private InventoryService inventoryService;
    private PaymentGateway paymentGateway;
    private ShippingService shippingService;
    private NotificationService notificationService;
    
    public OrderFacade(InventoryService inventoryService, PaymentGateway paymentGateway, ShippingService shippingService, NotificationService notificationService){
        this.inventoryService = inventoryService;
        this.paymentGateway = paymentGateway;
        this.shippingService = shippingService;
        this.notificationService = notificationService;
    }

    public boolean placeOrder(Order o1) {
        String productId = o1.getProductId();
        int quantity = o1.getQuantity();
        double amount = o1.getAmount();
        String cardNumber = o1.getCardNumber();
        String expiryDate = o1.getExpiryDate();
        String cvv = o1.getCvv();
        String shippingAddress = o1.getShippingAddress();
        String customerEmail = o1.getCustomerEmail();
        String customerPhone = o1.getCustomerPhone();
        try{
            System.out.println("\n--- Order Processing Started ---");
            
            // Step 1: Check Inventory
            if (!inventoryService.checkStock(productId, quantity)) {
                System.out.println("OrderProcessor: Stock not available for " + productId);
                return false;
            }

            // Step 2: Process Payment
            String transactionId = paymentGateway.processPayment(amount, cardNumber, expiryDate, cvv);
            if (transactionId == null) { // Simplified check
                System.out.println("OrderProcessor: Payment failed.");
                return false;
            }

             // Step 3: Update Inventory
            inventoryService.updateStock(productId, quantity);
            
            // Step 4: Create Shipment
            String orderDetails = "Product: " + productId + ", Qty: " + quantity + ", Amount: " + amount;
            String trackingId = shippingService.createShipment(shippingAddress, orderDetails);

            // Step 5: Send Notifications
            notificationService.sendEmail(customerEmail, "Order Confirmation", "Your order " + orderDetails + " is confirmed. Tracking: " + trackingId);
            notificationService.sendSms(customerPhone, "Your order is confirmed. Tracking: " + trackingId);

            return true;    
        }catch(Exception e){
            return false;
        }
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        PaymentGateway paymentGateway = new PaymentGateway();
        ShippingService shippingService = new ShippingService();
        NotificationService notificationService = new NotificationService();

        //OrderProcessor processor = new OrderProcessor();
        OrderFacade processor = new OrderFacade(inventoryService, paymentGateway, shippingService, notificationService);

        // Client code is verbose and tightly coupled to subsystem components
        Order o1 = new Order.OrderBuilder("LaptopX",1,1200.00)
                    .withPaymentDetails("1234-5678-9012-3456", "12/25", "123")
                    .toAddress("123 Main St, Anytown")
                    .forCustomer("customer@example.com", "555-1234")
                    .build();

        if(processor.placeOrder(o1))System.out.println("Order placed Successfully");

        // if(processor.placeOrder(
        //     "MouseY", 5, 75.00,
        //     "9876-5432-1098-7654", "01/24", "456",
        //     "456 Oak Ave, Otherville", "another@example.com", "555-5678"
        // )) System.out.println("Order placed Successfully");

        System.out.println("\nProblem: The client code (in main) directly interacts with many subsystem components.");
        System.out.println("This makes the client code complex, verbose, and tightly coupled to the subsystem's internal details.");
        System.out.println("Adding a new order processing step would require modifying the placeOrder method.");
    }
}
