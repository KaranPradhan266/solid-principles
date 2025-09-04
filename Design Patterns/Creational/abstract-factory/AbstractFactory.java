interface Button {
    public void paint();
    public void onClick();
}

interface Checkbox {
    public void paint();
    public void onSelect();
}


class WindowsButton implements Button{
    @Override
    public void paint(){
        System.out.println("Painting windows button!");
    }

    @Override
    public void onClick(){
        System.out.println("Windows button clicked");
    }
}
class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Painting a Windows-style checkbox.");
    }

    @Override
    public void onSelect() {
        System.out.println("Windows checkbox selected.");
    }
}

class MacButton implements Button{
    @Override
    public void paint() {
        System.out.println("Painting a macOS-style button.");
    }

    @Override
    public void onClick() {
        System.out.println("MacOS button clicked.");
    }
}

class MacOSCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Painting a macOS-style checkbox.");
    }

    @Override
    public void onSelect() {
        System.out.println("MacOS checkbox selected.");
    }
}

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WindowsFactory implements GUIFactory{
    @Override
    public Button createButton(){
        return new WindowsButton();
    }
    @Override
    public Checkbox createCheckbox(){
        return new WindowsCheckbox();
    }
}

class MacFactory implements GUIFactory {
    @Override  
    public Button createButton() {
        return new MacButton();
    }
    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

class Application {
    private final Button button;
    private final Checkbox checkbox;

    public Application(GUIFactory factory){
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    void renderUI(){
        button.paint();
        checkbox.paint();
    }
}

public class AbstractFactory {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        GUIFactory factory;

        if(os.contains("Windows")) factory  = new WindowsFactory();
        else factory = new MacFactory();
        
        Application app = new Application(factory);
        app.renderUI();
    }
}

