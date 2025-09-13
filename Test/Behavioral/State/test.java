interface MachineState {
    void changeLight(TrafficLight trafficLight);
    void pressButton(TrafficLight trafficLight);
}

class RedState implements MachineState{
    @Override
    public void changeLight(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        trafficLight.setCurrentState(new GreenState());
        System.out.println("Traffic Light is now: " + trafficLight.getCurrentState());
    }
    
    @Override
    public void pressButton(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        System.out.println("Traffic Light: No change, still RED.");
    }
}

class YellowState implements MachineState{

    @Override
    public void changeLight(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        trafficLight.setCurrentState(new RedState());
        System.out.println("Traffic Light is now: " + trafficLight.getCurrentState());
    }

    @Override
    public void pressButton(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        System.out.println("Traffic Light: No change, still YELLOW.");
    }
}

class GreenState implements MachineState{

    @Override
    public void changeLight(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        trafficLight.setCurrentState(new YellowState());
        System.out.println("Traffic Light is now: " + trafficLight.getCurrentState());
    }

    @Override
    public void pressButton(TrafficLight trafficLight) {
        // TODO Auto-generated method stub
        trafficLight.setCurrentState(new YellowState()); // Immediately go to yellow
        System.out.println("Traffic Light is now: " + trafficLight.getCurrentState() + " (due to button press)");
    }
}

// Problematic Context - Behavior changes based on internal state using conditional logic
class TrafficLight {
    private MachineState currentState; // Can be "RED", "YELLOW", "GREEN"

    public TrafficLight() {
        currentState = new RedState(); // Initial state
        System.out.println("Traffic Light initialized to: " + currentState);
    }

    public MachineState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MachineState currentState){
        this.currentState = currentState;
    }

    public void changeLight() {
        System.out.println("\nTraffic Light: Changing light...");
        currentState.changeLight(this);
    }

    public void pressButton() {
        System.out.println("Traffic Light: Button pressed.");
        currentState.pressButton(this);
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();

        light.changeLight(); // RED -> GREEN
        light.pressButton(); // GREEN -> YELLOW
        light.changeLight(); // YELLOW -> RED
        light.pressButton(); // RED (no change)
        light.changeLight(); // RED -> GREEN
        light.changeLight(); // GREEN -> YELLOW
        light.changeLight(); // YELLOW -> RED

        System.out.println("\nProblem: The TrafficLight class has complex conditional logic (if-else/switch) based on its state.");
        System.out.println("Adding a new state (e.g., Flashing Yellow) or new behavior would require modifying existing methods.");
        System.out.println("This violates the Open/Closed Principle.");
    }
}
