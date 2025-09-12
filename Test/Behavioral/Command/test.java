interface Command {
    void execute();
}

class OpenFile implements Command {
    private TextEditor editor;
    private String filename;

    public OpenFile(TextEditor editor, String filename){
        this.editor = editor;
        this.filename = filename;
    }

    @Override
    public void execute() {
        editor.openFile(filename);
    }
}

class SaveFile implements Command {
    private TextEditor editor;

    public SaveFile(TextEditor editor){
        this.editor = editor;
    }
    @Override
    public void execute() {
        editor.saveFile();
    }
}

class PrintDocument implements Command {
    private TextEditor editor;
    public PrintDocument(TextEditor editor){
        this.editor = editor;
    }
    @Override
    public void execute() {
        editor.printDocument();
    }
}
// Receiver: The object that performs the actual actions
class TextEditor {
    private String currentFile = "No file open";
    private String content = "";

    public void openFile(String filename) {
        this.currentFile = filename;
        this.content = "Content of " + filename; // Simulate loading content
        System.out.println("Editor: Opened file '" + filename + "'");
    }

    public void saveFile() {
        System.out.println("Editor: Saved file '" + currentFile + "' with content: '" + content + "'");
    }

    public void printDocument() {
        System.out.println("Editor: Printing document '" + currentFile + "'");
    }
}

// Problematic Invoker: Directly calls methods on the Receiver
class EditorMenu {
    //private TextEditor editor;
    private Command openFile;
    private Command saveFile;
    private Command printDocument;

    public EditorMenu(Command openFile, Command saveFile, Command printDocument) {
        this.openFile = openFile;
        this.saveFile = saveFile;
        this.printDocument = printDocument;
    }

    public void clickOpenButton() {
        System.out.println("Menu: 'Open' button clicked.");
        //editor.openFile(filename); // Direct call
        openFile.execute();
    }

    public void clickSaveButton() {
        System.out.println("Menu: 'Save' button clicked.");
        //editor.saveFile(); // Direct call
        saveFile.execute();
    }

    public void clickPrintButton() {
        System.out.println("Menu: 'Print' button clicked.");
        //editor.printDocument(); // Direct call
        printDocument.execute();
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Command openFile = new OpenFile(editor, "Example");
        Command saveFile = new SaveFile(editor);
        Command printDocument  = new PrintDocument(editor);

        EditorMenu menu = new EditorMenu(openFile, saveFile, printDocument);

        System.out.println("--- Demonstrating Direct Invocation (Problem) ---");

        menu.clickOpenButton();
        menu.clickSaveButton();
        menu.clickPrintButton();

        System.out.println("\nProblem: The EditorMenu is tightly coupled to the TextEditor.");
        System.out.println("Adding features like undo/redo, queuing, or logging these actions would require significant changes to EditorMenu.");
    }
}
