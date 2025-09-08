// 1. Subject Interface
interface Document {
    String read();
}

class RealDocumentProxy implements Document{
    private RealDocument realDocument;
    private User user;

    public RealDocumentProxy(RealDocument realDocument, User user) {
        this.realDocument = realDocument;
        this.user = user;
    }

    private boolean checkAccess(User user){
        return user.isAdmin();
    }

    @Override
    public String read() {
        if(!checkAccess(user)){
            System.out.println("Not Admin");
            return "Access Denied!";
        }

        return realDocument.read();
    }
}

// 2. Real Subject (Sensitive Resource)
class RealDocument implements Document {
    private String content;
    private String name;

    public RealDocument(String name, String content) {
        this.name = name;
        this.content = content;
        System.out.println("RealDocument: Loading '" + name + "' from storage...");
        try {
            Thread.sleep(1000); // Simulate loading delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String read() {
        System.out.println("RealDocument: Reading content of '" + name + "'");
        return content;
    }
}

// User class (for access control)
class User {
    private String username;
    private boolean isAdmin;

    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

// Problematic Client - Directly accesses sensitive document without control
class DocumentReader {
    public void displayDocument(Document doc, User user) {

        System.out.println("\nDocumentReader: Attempting to display document for user " + user.getUsername());
        // Problem: No access control here. Any user can read the document directly.
        String content = doc.read();
        System.out.println(
                "DocumentReader: Displaying content:\n--- START CONTENT ---\n" + content + "\n--- END CONTENT ---");
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        RealDocument sensitiveDoc = new RealDocument("Secret Plans",
                "Top secret plans for Project X. Only for authorized personnel.");

        User adminUser = new User("AdminUser", true);
        User regularUser = new User(
                "RegularUser", false);

        DocumentReader reader = new DocumentReader();

        RealDocumentProxy protectionProxy1 = new RealDocumentProxy(sensitiveDoc, regularUser);
        RealDocumentProxy protectionProxy2 = new RealDocumentProxy(sensitiveDoc, adminUser);

        System.out.println("--- Scenario 1: Admin User Access ---");
        reader.displayDocument(protectionProxy2, adminUser);

        System.out.println("\n--- Scenario 2: Regular User Access (Problematic) ---");
        // Problem: Regular user can also access the sensitive document directly.
        reader.displayDocument(protectionProxy1, regularUser);

        System.out.println(
                "\nProblem: Direct access to RealDocument allows unauthorized users to read sensitive information.");
        System.out.println("The DocumentReader client is tightly coupled to RealDocument and lacks access control.");
    }
}
