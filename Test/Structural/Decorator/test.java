interface Car {
    double getCost();
    String getDescription();
}

// // Base Car class
// class Car {
    
// }

// Problematic way to add features using inheritance (leads to class explosion)
class BasicCar implements Car {
    // No additional features

    @Override
    public double getCost() {
        return 15000.0; // Base cost of a car
    }

    @Override
    public String getDescription() {
        return "Basic Car";
    }
}

abstract class CarDecorator implements Car{
    protected final Car decoratedCar;

    CarDecorator(Car car){
        this.decoratedCar = car;
    }

    @Override
    public double getCost() {
        return decoratedCar.getCost();
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription();
    }
}

class NavigationCarDecorator extends CarDecorator {
    NavigationCarDecorator(Car car){
        super(car);
    }

    @Override
    public double getCost() {
        return decoratedCar.getCost() + 1500.0;
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription() + ", Navigation";
    }
}

class LeatherSeatsCarDecorator extends CarDecorator {
    LeatherSeatsCarDecorator(Car car){
        super(car);
    }
    @Override
    public double getCost() {
        return decoratedCar.getCost() + 2000.0;
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription() + ", Leather Seats";
    }
}

class SunroofCarDecorator extends CarDecorator {
    SunroofCarDecorator(Car car){
        super(car);
    }
    @Override
    public double getCost() {
        return decoratedCar.getCost() + 1000.0;
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription() + ", Sunroof";
    }
}

// What if you want Navigation AND Leather Seats?
// You'd need:
// class NavigationLeatherSeatsCar extends NavigationCar { ... }
// Or:
// class LeatherSeatsNavigationCar extends LeatherSeatsCar { ... }
// This quickly gets out of hand with more features.

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        System.out.println("--- Demonstrating Feature Addition via Inheritance (Problem) ---");

        Car car1 = new BasicCar();
        System.out.println("Car 1: " + car1.getDescription() + " Cost: $" + car1.getCost());

        Car car2 = new NavigationCarDecorator(new BasicCar());
        System.out.println("Car 2: " + car2.getDescription() + " Cost: $" + car2.getCost());

        Car car3 = new SunroofCarDecorator(new LeatherSeatsCarDecorator(new BasicCar()));
        System.out.println("Car 3: " + car3.getDescription() + " Cost: $" + car3.getCost());

        // Problem: How to get a car with Navigation AND Leather Seats?
        // You'd need a new class like NavigationLeatherSeatsCar
        // Car car4 = new NavigationLeatherSeatsCar(); // This class doesn't exist yet!

        System.out.println("\nProblem: Adding features via inheritance leads to a combinatorial explosion of classes.");
        System.out.println("It's inflexible and hard to manage all possible combinations of features.");
        System.out.println("Features are added statically at compile time, not dynamically at runtime.");
    }
}
