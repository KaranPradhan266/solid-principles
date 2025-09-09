// 1. The Implementation (Device) Interface
// This defines the interface for implementation classes.
interface Device {
    void turnOn();
    void turnOff();
    void setChannel(int channel);
    int getChannel(); // Added for channelUp/Down logic
    void setVolume(int volume); // Added for mute logic
}

// 2. Concrete Implementations
// These are the concrete classes that implement the Device interface.
class Tv implements Device {
    private int channel = 1;
    private int volume = 10;

    @Override
    public void turnOn() { System.out.println("TV: On"); }
    @Override
    public void turnOff() { System.out.println("TV: Off"); }
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("TV: Channel set to " + this.channel);
    }
    @Override
    public int getChannel() { return channel; }
    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("TV: Volume set to " + this.volume);
    }
}

class Radio implements Device {
    private int channel = 1;
    private int volume = 5;

    @Override
    public void turnOn() { System.out.println("Radio: On"); }
    @Override
    public void turnOff() { System.out.println("Radio: Off"); }
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("Radio: Channel set to " + this.channel);
    }
    @Override
    public int getChannel() { return channel; }
    @Override
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Radio: Volume set to " + this.volume);
    }
}

// 3. The Abstraction (RemoteControl)
// This defines the abstraction's interface and maintains a reference to an object
// of type Device (the implementation interface).
abstract class RemoteControl {
    protected Device device; // Bridge to the implementation

    public RemoteControl(Device device) {
        this.device = device;
    }

    public abstract void powerOn();
    public abstract void powerOff();
    public void channelUp() { // Default implementation for channelUp
        device.setChannel(device.getChannel() + 1);
    }
    public void channelDown() { // Default implementation for channelDown
        device.setChannel(device.getChannel() - 1);
    }
}

// 4. Refined Abstractions
// These are the concrete abstractions that extend RemoteControl.
class BasicRemote extends RemoteControl {
    public BasicRemote(Device device) {
        super(device);
    }

    @Override
    public void powerOn() {
        System.out.println("Basic Remote: Powering on device...");
        device.turnOn();
    }

    @Override
    public void powerOff() {
        System.out.println("Basic Remote: Powering off device...");
        device.turnOff();
    }
}

class AdvancedRemote extends RemoteControl {
    public AdvancedRemote(Device device) {
        super(device);
    }

    @Override
    public void powerOn() {
        System.out.println("Advanced Remote: Powering on device...");
        device.turnOn();
    }

    @Override
    public void powerOff() {
        System.out.println("Advanced Remote: Powering off device...");
        device.turnOff();
    }

    public void mute() {
        System.out.println("Advanced Remote: Muting device...");
        device.setVolume(0);
    }
}

// Main class to demonstrate the Bridge pattern
public class Bridge {
    public static void main(String[] args) {
        // Create a TV and a Basic Remote for it
        Device tv = new Tv();
        RemoteControl basicTvRemote = new BasicRemote(tv);
        basicTvRemote.powerOn();
        basicTvRemote.channelUp();
        basicTvRemote.channelDown();
        basicTvRemote.powerOff();

        System.out.println("\n--------------------\n");

        // Create a Radio and an Advanced Remote for it
        Device radio = new Radio();
        RemoteControl advancedRadioRemote = new AdvancedRemote(radio);
        advancedRadioRemote.powerOn();
        advancedRadioRemote.channelUp();
        ((AdvancedRemote) advancedRadioRemote).mute(); // Advanced remote specific feature
        advancedRadioRemote.powerOff();
    }
}
