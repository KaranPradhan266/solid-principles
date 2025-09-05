// 1. Component Interface
interface Coffee {
    double getCost();
    String getDescription();
}

// 2. Concrete Component
class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 5.0; // Base cost of a simple coffee
    }

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }
}

// 3. Abstract Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost(); // Delegate to the wrapped object
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription(); // Delegate to the wrapped object
    }
}

// 4. Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 1.5; // Add cost of milk
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Milk"; // Add milk to description
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5; // Add cost of sugar
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Sugar"; // Add sugar to description
    }
}

class CaramelDecorator extends CoffeeDecorator {
    public CaramelDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public double getCost() {
        return super.getCost() + 2.0; // Add cost of caramel
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with Caramel"; // Add caramel to description
    }
}

// Main class to demonstrate the Decorator pattern
public class Decorator {
    public static void main(String[] args) {
        // Order a simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println("Order 1: " + coffee.getDescription() + " Cost: $" + coffee.getCost());

        // Order a coffee with milk
        Coffee milkCoffee = new MilkDecorator(new SimpleCoffee());
        System.out.println("Order 2: " + milkCoffee.getDescription() + " Cost: $" + milkCoffee.getCost());

        // Order a coffee with milk and sugar
        Coffee milkSugarCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println("Order 3: " + milkSugarCoffee.getDescription() + " Cost: $" + milkSugarCoffee.getCost());

        // Order a coffee with milk, sugar, and caramel
        Coffee complexCoffee = new CaramelDecorator(new SugarDecorator(new MilkDecorator(new SimpleCoffee())));
        System.out.println("Order 4: " + complexCoffee.getDescription() + " Cost: $" + complexCoffee.getCost());

        // You can also decorate an already decorated object
        Coffee anotherCoffee = new SimpleCoffee();
        anotherCoffee = new MilkDecorator(anotherCoffee); // Add milk
        anotherCoffee = new CaramelDecorator(anotherCoffee); // Add caramel
        System.out.println("Order 5: " + anotherCoffee.getDescription() + " Cost: $" + anotherCoffee.getCost());
    }
}
