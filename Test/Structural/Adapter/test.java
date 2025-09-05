import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

// Existing Client's Expected Interface (Target)
interface DataSource {
    String readData();
}

// Compatible Implementation for the Client
class FileReader implements DataSource {
    private String fileContent;

    public FileReader(String content) {
        this.fileContent = content;
    }

    @Override
    public String readData() {
        System.out.println("FileReader: Reading data from file...");
        return fileContent;
    }
}

class LegacyDataSystemAdapter implements DataSource{
    private final LegacyDataSystem legacyDataSystem;

    public LegacyDataSystemAdapter(LegacyDataSystem legacyDataSystem){
        this.legacyDataSystem = legacyDataSystem;
    }

    @Override
    public String readData(){
        List<String> records = legacyDataSystem.fetchRecords();
        String combinedData = String.join(", ", records);
        return combinedData;
    }
}

// Existing Client
class DataProcessor {
    private DataSource dataSource;

    public DataProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void process() {
        System.out.println("DataProcessor: Starting data processing...");
        String data = dataSource.readData();
        System.out.println("DataProcessor: Received data: \"" + data + "\"");
        System.out.println("DataProcessor: Finished data processing.");
    }
}

// Incompatible Component (Adaptee) - from a legacy system or third-party library
class LegacyDataSystem {
    public List<String> fetchRecords() {
        System.out.println("LegacyDataSystem: Fetching records from old system...");
        return Arrays.asList("Record A", "Record B", "Record C");
    }

    public String getRecordById(int id) {
        System.out.println("LegacyDataSystem: Getting record by ID: " + id);
        return "Record " + (char)('A' + id);
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        System.out.println("--- Using Compatible FileReader ---");
        DataSource fileSource = new FileReader("Hello from file!");
        DataProcessor processor1 = new DataProcessor(fileSource);
        processor1.process();

        System.out.println("\n--- Attempting to use LegacyDataSystem directly (Problem) ---");
        LegacyDataSystem legacyDataSystem = new LegacyDataSystem();
        LegacyDataSystemAdapter legacySystemAdapter = new LegacyDataSystemAdapter(legacyDataSystem);
        // This line will cause a compilation error because LegacyDataSystem is not a DataSource
        DataProcessor processor2 = new DataProcessor(legacySystemAdapter); // <-- This line will cause an error
        processor2.process();

        System.out.println("Problem: LegacyDataSystem cannot be directly used by DataProcessor.");
        System.out.println("Its interface (fetchRecords, getRecordById) is incompatible with DataSource (readData).");
        System.out.println("You need an Adapter to bridge this gap.");
    }
}
