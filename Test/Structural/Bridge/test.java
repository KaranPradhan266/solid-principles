// Problematic Notification Types (Abstraction and Implementation are tightly coupled)

import javax.sound.midi.MetaEventListener;

interface MessageSender {
    void send(String message);
}


class PlainTextSender implements MessageSender {
    
    @Override
    public void send(String message){
        System.out.println("Sending in plain-text format");
    }
}

class HtmlSender implements MessageSender{

    @Override
    public void send(String message){
        System.out.println("Sending in HTML format");
    }
}

abstract class Notification {
    protected MessageSender messageSender;

    public Notification(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    public abstract void send(String message);
}


class SmsNotification extends Notification {

    public SmsNotification(MessageSender messageSender){
        super(messageSender);
    }

    @Override
    public void send(String message) {
        System.out.println("SMS");
        messageSender.send(message);
    }
}

class EmailNotification extends Notification {


    public EmailNotification(MessageSender messageSender){
        super(messageSender);
    }

    @Override
    public void send(String message){
        System.out.println("Email");
        messageSender.send(message);
    }
}



// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        MessageSender plainTextSender = new PlainTextSender();
        MessageSender htmlSender = new HtmlSender();

        Notification Email = new EmailNotification(htmlSender);
        Notification SMS = new SmsNotification(plainTextSender);

        System.out.println("--- Sending Notifications (Problem) ---");

        SMS.send("Hello World!");
        Email.send("<h1>Welcome!</h1>");
    }
}
