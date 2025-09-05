// Subsystem Components
class Amplifier {
    public void on() { System.out.println("Amplifier On"); }
    public void off() { System.out.println("Amplifier Off"); }
    public void setVolume(int volume) { System.out.println("Amplifier volume set to " + volume); }
    public void setSource(String source) { System.out.println("Amplifier source set to " + source); }
}

class Tuner {
    public void on() { System.out.println("Tuner On"); }
    public void off() { System.out.println("Tuner Off"); }
    public void setAm() { System.out.println("Tuner set to AM"); }
    public void setFm() { System.out.println("Tuner set to FM"); }
    public void setFrequency(double frequency) { System.out.println("Tuner frequency set to " + frequency); }
}

class DvdPlayer {
    public void on() { System.out.println("DVD Player On"); }
    public void off() { System.out.println("DVD Player Off"); }
    public void play(String movie) { System.out.println("DVD Player playing \"" + movie + "\""); }
    public void pause() { System.out.println("DVD Player paused"); }
    public void stop() { System.out.println("DVD Player stopped"); }
}

class Projector {
    public void on() { System.out.println("Projector On"); }
    public void off() { System.out.println("Projector Off"); }
    public void wideScreenMode() { System.out.println("Projector in widescreen mode"); }
}

class Lights {
    public void on() { System.out.println("Lights On"); }
    public void off() { System.out.println("Lights Off"); }
    public void dim(int level) { System.out.println("Lights dimmed to " + level + "%"); }
}

// The Facade
class HomeTheaterFacade {
    Amplifier amp;
    Tuner tuner;
    DvdPlayer dvd;
    Projector projector;
    Lights lights;

    public HomeTheaterFacade(Amplifier amp, Tuner tuner, DvdPlayer dvd, Projector projector, Lights lights) {
        this.amp = amp;
        this.tuner = tuner;
        this.dvd = dvd;
        this.projector = projector;
        this.lights = lights;
    }

    public void watchMovie(String movie) {
        System.out.println("\nGet ready to watch a movie...");
        lights.dim(10);
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setSource("DVD");
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }

    public void endMovie() {
        System.out.println("\nShutting down movie theater...");
        lights.on();
        dvd.stop();
        dvd.off();
        amp.off();
        projector.off();
    }

    public void listenToRadio(double frequency) {
        System.out.println("\nListening to the radio...");
        tuner.on();
        tuner.setFm();
        tuner.setFrequency(frequency);
        amp.on();
        amp.setSource("Tuner");
        amp.setVolume(3);
    }

    public void endRadio() {
        System.out.println("\nShutting down the radio...");
        tuner.off();
        amp.off();
    }
}

// Main class to demonstrate the Facade
public class Facade {
    public static void main(String[] args) {
        // Instantiate the subsystem components
        Amplifier amp = new Amplifier();
        Tuner tuner = new Tuner();
        DvdPlayer dvd = new DvdPlayer();
        Projector projector = new Projector();
        Lights lights = new Lights();

        // Create the Facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, tuner, dvd, projector, lights);

        // Use the Facade to perform complex operations simply
        homeTheater.watchMovie("The Matrix");
        homeTheater.endMovie();

        homeTheater.listenToRadio(101.9);
        homeTheater.endRadio();
    }
}
