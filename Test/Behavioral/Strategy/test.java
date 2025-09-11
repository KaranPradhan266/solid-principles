interface ShippingStrategy {
    double calculateCost(double weight);
}


class StandardStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return weight * 2.5;
    }
}

class ExpressStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return weight * 5.0 + 10.0;
    }
}

class InternationalStrategy implements ShippingStrategy {

    @Override
    public double calculateCost(double weight){
        return weight * 10.0 + 50.0;
    }
}





// Problematic Context - Contains conditional logic for different algorithms
class ShippingCalculator {
    private ShippingStrategy shippingStrategy;

    public ShippingCalculator(ShippingStrategy shippingStrategy){
        this.shippingStrategy = shippingStrategy;
    }

    public double calculateShippingCost(double weight) {
        double cost = 0;
        System.out.println("\nCalculating shipping");

        // if (shippingMethod.equalsIgnoreCase("standard")) {
        //     cost = weight * 2.5; // $2.5 per kg
        //     System.out.println("Using Standard Shipping.");
        // } else if (shippingMethod.equalsIgnoreCase("express")) {
        //     cost = weight * 5.0 + 10.0; // $5.0 per kg + $10 flat fee
        //     System.out.println("Using Express Shipping.");
        // } else if (shippingMethod.equalsIgnoreCase("international")) {
        //     cost = weight * 10.0 + 50.0; // $10.0 per kg + $50 flat fee
        //     System.out.println("Using International Shipping.");
        // } else {
        //     System.out.println("Error: Unknown shipping method.");
        //     return -1; // Indicate error
        // }
        cost += shippingStrategy.calculateCost(weight);
        return cost;
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        InternationalStrategy internationalStrategy = new InternationalStrategy();
        StandardStrategy standardStrategy = new StandardStrategy();
        ExpressStrategy expressStrategy = new ExpressStrategy();

        double cost1 = new ShippingCalculator(standardStrategy).calculateShippingCost(5.0);
        System.out.println("Total cost: $" + cost1);

        double cost2 = new ShippingCalculator(expressStrategy).calculateShippingCost(2.0);
        System.out.println("Total cost: $" + cost2);

        double cost3 = new ShippingCalculator(internationalStrategy).calculateShippingCost(10.0);
        System.out.println("Total cost: $" + cost3);

        // double cost4 = calculator.calculateShippingCost(3.0, "priority"); // Unknown method
        // System.out.println("Total cost: $" + cost4);

        System.out.println("\nProblem: The ShippingCalculator is tightly coupled to specific shipping methods.");
        System.out.println("Adding a new shipping method (e.g., Overnight) requires modifying the calculateShippingCost method.");
        System.out.println("This violates the Open/Closed Principle and leads to complex conditional logic.");
    }
}
