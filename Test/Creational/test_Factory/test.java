// The product interface

import javax.print.Doc;

interface Document {
    void open();
    void close();
    void save();
}

// Concrete products
class TextDocument implements Document {
    @Override
    public void open() { System.out.println("Opening text document..."); }
    @Override
    public void close() { System.out.println("Closing text document..."); }
    @Override
    public void save() { System.out.println("Saving text document..."); }
}

class SpreadsheetDocument implements Document {
    @Override
    public void open() { System.out.println("Opening spreadsheet document..."); }
    @Override
    public void close() { System.out.println("Closing spreadsheet document..."); }
    @Override
    public void save() { System.out.println("Saving spreadsheet document..."); }
}

class PresentationDocument implements Document {
    @Override
    public void open() { System.out.println("Opening presentation document..."); }
    @Override
    public void close() { System.out.println("Closing presentation document..."); }
    @Override
    public void save() { System.out.println("Saving presentation document..."); }
}

// The "Creator" class with a problematic creation logic
abstract class Application {
    // This is the part that violates the Open/Closed Principle.
    // If we want to add a new document type (e.g., a DrawingDocument),
    // we have to modify this method.
    public abstract Document createDocument();

    // Some other business logic that uses the created document
    public void newDocument(String type) {
        System.out.println("Application: Creating a new document of type " + type);
        Document doc = createDocument();
        doc.open();
        doc.save();
    }
}

class TextDocumentCreator extends Application{
    @Override
    public Document createDocument(){
        return new TextDocument();
    }
}

class SpreadsheetDocumentCreator extends Application{
    @Override
    public Document createDocument(){
        return new SpreadsheetDocument();
    }
}

class PresentationDocumentCreator extends Application {
    @Override 
    public Document createDocument(){
        return new PresentationDocument();
    }
}

// The main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        Application app = new TextDocumentCreator();

        app.newDocument("Text");
        System.out.println("---");
        app = new SpreadsheetDocumentCreator();
        app.newDocument("SpreadSheet");
        System.out.println("---");

        // What if we want to add a new "DrawingDocument"?
        // We would have to go back and modify the Application class.
        // This is the problem that the Factory Method pattern solves.
        System.out.println("Problem: The Application class is tightly coupled to the concrete document classes.");
        System.out.println("To add a new document type, you must modify the Application class's createDocument method.");
    }
}
