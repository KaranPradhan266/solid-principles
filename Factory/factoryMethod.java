/*It’s particularly useful in situations where:

The exact type of object to be created isn't known until runtime.
Object creation logic is complex, repetitive, or needs encapsulation.
You want to follow the Open/Closed Principle, open for extension, closed for modification. */

// class NotificationService {
//     public void sendNotification(String type, String message) {
//         if (type.equals("EMAIL")) {
//             EmailNotification email = new EmailNotification();
//             email.send(message);
//         } else if (type.equals("SMS")) {
//             SMSNotification sms = new SMSNotification();
//             sms.send(message);
//         } else if (type.equals("Push")) {
//             PushNotification sms = new PushNotification();
//             sms.send(message);
//         } else if (type.equals("Slack")) {
//             SlackNotification sms = new SlackNotification();
//             sms.send(message);
//         } else if (type.equals("WhatsApp")) {
//             WhatsAppNotification sms = new WhatsAppNotification();
//             sms.send(message);
//         }
//     }
// }

//Instead of above, we can separate what to create logic and how to use it!

// import javax.management.Notification;

// class SimpleNotificationFactory {
//     public static Notification createNotification(String type){
//         return switch(type){
//             case "Email" -> new EmailNotification();
//             case "SMS" -> new SMSNotification();
//             case "PUSH" -> new PushNotification();
//             default -> throw new IllegalArgumentException("Unknown type");
//         };
//     }
// }

// class NotificationService { 
//     public void sendNotification(String type, String msg){
//         Notification notification = SimpleNotificationFactory.createNotification(type);
//         notification.send(msg);

//     }
// }

//Define the product interface
interface Notification {
    public void send(String msg);
}

//Define Concreate products
class EmailNotification implements Notification {
    @Override
    public void send(String msg){
        System.out.println("Sending email : "+ msg);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String msg){
        System.out.println("Sending SMS: " + msg);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending push notification: " + message);
    }
}

class SlackNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SLACK: " + message);
    }
}

//Define abstract Creator
abstract class NotificationCreator {
    //Factory Method
    public abstract Notification createNotification();

    //Common logic using the factory method
    public void send(String msg){
        Notification notification = createNotification();
        notification.send(msg);
    }
}

//Above class is use to define the flow!

class EmailNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification(){
        return new EmailNotification();
    }
}

class SMSNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification(){
        return new SMSNotification();
    }
}

class PushNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new PushNotification();
    }
}


public class factoryMethod {
    public static void main(String[] args) {
        NotificationCreator creator;
        creator = new EmailNotificationCreator();
        creator.send("hi");

        creator = new PushNotificationCreator();
        creator.send("hello!");
    }
}




