import java.util.Stack; // Keep Stack import

interface Command {
    void execute();
    void undo();
}

class LightsOn implements Command {
    private Light light;

    public LightsOn(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.switchOn();        
    }

    @Override
    public void undo() {
        light.switchOff();        
    }

}

class LightsOff implements Command {
    private Light light;

    public LightsOff(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.switchOff();
    }

    @Override
    public void undo() {
        light.switchOn();        
    }
}

class SetTemperature implements Command {
    private int temperature;
    private Thermostat thermostat;
    private int previousTemperature;

    public SetTemperature(Thermostat thermostat, int temperature){
        this.thermostat = thermostat;
        this.temperature = temperature;
    }

    @Override
    public void execute() {
        previousTemperature = thermostat.getTemperature();
        thermostat.setTemp(temperature);
    }

    @Override
    public void undo() {
        thermostat.setTemp(previousTemperature);
    }
}

class Light { 
    private boolean isOn;

    public Light(){
        this.isOn = false;
    }

    public void switchOn(){
        if(!isOn){
            System.out.println("Lights on!");
            isOn = true;
        }
    }

    public void switchOff(){
        if(isOn){
            System.out.println("Lights off!");
            isOn = false;
        }
    }
}


class Thermostat {
    private int temperature;

    public Thermostat(){
        this.temperature = 25;
    }

    public void setTemp(int temperature){
        System.out.println("Temperature now set to:" + temperature);
        this.temperature = temperature;
    }

    public int getTemperature(){
        return temperature;
    }
}

class SmartHome {
    Stack<Command> commandStack = new Stack<>();
    Stack<Command> redoStack = new Stack<>(); // New stack for redo

    void setCommand(Command command){
        command.execute();
        commandStack.push(command);
        redoStack.clear(); // Clear redo history on new command
    }

    void undo(){
        if(!commandStack.isEmpty()){
            Command undoneCommand = commandStack.pop();
            undoneCommand.undo();
            redoStack.push(undoneCommand); // Push to redo stack
        }
    }

    void redo(){
        if(!redoStack.isEmpty()){
            Command redoneCommand = redoStack.pop();
            redoneCommand.execute(); // Re-execute the command
            commandStack.push(redoneCommand); // Push back to command stack
        }
    }
}

class MainClass { // Keep MainClass for now, as per previous discussion
    public static void main(String[] args) {
        Light light = new Light();
        Thermostat thermostat = new Thermostat();

        Command lightsOn = new LightsOn(light);
        Command lightsOff  = new LightsOff(light);

        SmartHome smartHome = new SmartHome();

        System.out.println("--- Executing Commands ---");
        smartHome.setCommand(lightsOn);
        smartHome.setCommand(new SetTemperature(thermostat, 25));
        smartHome.setCommand(new SetTemperature(thermostat, 50));
        smartHome.setCommand(lightsOff);

        System.out.println("\n--- Performing Undos ---");
        smartHome.undo(); // Undo LightsOff
        smartHome.undo(); // Undo SetTemperature(50)

        System.out.println("\n--- Performing Redos ---");
        smartHome.redo(); // Redo SetTemperature(50)
        smartHome.redo(); // Redo LightsOff

        System.out.println("\n--- Final State ---");
        smartHome.setCommand(lightsOn); // Just to show final state after redo
    }
}
