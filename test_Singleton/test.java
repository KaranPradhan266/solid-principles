import java.util.HashMap;
import java.util.Map;

// This is the class that should be a Singleton, but it's not.
class ConfigurationManager {
    private Map<String, String> settings = new HashMap<>();
    
    private ConfigurationManager() {
        // Simulate loading configuration from a file, which is an expensive operation.
        System.out.println("ConfigurationManager instance created. Loading settings...");
        settings.put("database.url", "jdbc:mysql://localhost:3306/mydb");
        settings.put("logging.level", "INFO");
    }

    private static class helper {
        private static final ConfigurationManager instance = new ConfigurationManager();

    }
    public static ConfigurationManager getInstance(){
        return helper.instance;
    }

    public String getSetting(String key) {
        return settings.get(key);
    }

    public void setSetting(String key, String value) {
        System.out.println("Setting '" + key + "' to '" + value + "'");
        settings.put(key, value);
    }
}

// A client class that uses the ConfigurationManager.
class DatabaseConnection {
    private ConfigurationManager configManager;

    public DatabaseConnection() {
        // Tightly coupled: creates its own instance of ConfigurationManager.
        this.configManager = ConfigurationManager.getInstance();
    }

    public void connect() {
        String dbUrl = configManager.getSetting("database.url");
        System.out.println("Connecting to database at: " + dbUrl);
    }

    public void updateDbUrl(String newUrl) {
        configManager.setSetting("database.url", newUrl);
    }
}

// Another client class that also uses the ConfigurationManager.
class LoggingService {
    private ConfigurationManager configManager;

    public LoggingService() {
        // Tightly coupled: creates its own instance of ConfigurationManager.
        this.configManager = ConfigurationManager.getInstance();
    }

    public void log(String message) {
        String logLevel = configManager.getSetting("logging.level");
        System.out.println("[" + logLevel + "] " + message);
    }
}

// The main class to demonstrate the problem.
public class test {
    public static void main(String[] args) {
        System.out.println("--- Running application ---");

        DatabaseConnection dbConnection = new DatabaseConnection();
        dbConnection.connect();

        LoggingService loggingService = new LoggingService();
        loggingService.log("Application started.");

        System.out.println("\n--- Updating database URL in one part of the application ---");
        dbConnection.updateDbUrl("jdbc:mysql://prod-db:3306/prod");

        System.out.println("\n--- Creating a new database connection to see if it gets the updated URL ---");
        DatabaseConnection newDbConnection = new DatabaseConnection();
        newDbConnection.connect(); // This will print the OLD URL, because it creates a new ConfigurationManager.

        System.out.println("\nProblem demonstrated: Multiple instances of ConfigurationManager exist, leading to inconsistent state.");
    }
}
