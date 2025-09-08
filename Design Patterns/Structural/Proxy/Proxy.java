// 1. Subject Interface
interface Image {
    void display();
}

// 2. Real Subject
class RealImage implements Image {
    private String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk(fileName); // Simulate heavy loading
    }

    @Override
    public void display() {
        System.out.println("Displaying " + fileName);
    }

    private void loadFromDisk(String fileName) {
        System.out.println("Loading " + fileName + " from disk...");
        try {
            Thread.sleep(2000); // Simulate a delay for loading
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 3. Proxy
class ProxyImage implements Image {
    private String fileName;
    private RealImage realImage; // Reference to the RealImage

    public ProxyImage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName); // Lazy initialization: load only when needed
        }
        realImage.display();
    }
}

// Main class to demonstrate the Proxy pattern
public class Proxy {
    public static void main(String[] args) {
        // Image will not be loaded until display() is called
        Image image1 = new ProxyImage("photo1.jpg");
        Image image2 = new ProxyImage("photo2.png");

        System.out.println("--- First call to display() for image1 (loads from disk) ---");
        image1.display(); // Image will be loaded from disk

        System.out.println("\n--- Second call to display() for image1 (already loaded) ---");
        image1.display(); // Image will not be loaded again

        System.out.println("\n--- First call to display() for image2 (loads from disk) ---");
        image2.display(); // Image will be loaded from disk
    }
}
