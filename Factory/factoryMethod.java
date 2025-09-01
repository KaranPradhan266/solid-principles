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

import javax.management.Notification;

class SimpleNotificationFactory {
    public static Notification createNotification(String type){
        return switch(type){
            case "Email" -> new EmailNotification();
            case "SMS" -> new SMSNotification();
            case "PUSH" -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}

class NotificationService { 
    public void sendNotification(String type, String msg){
        Notification notification = SimpleNotificationFactory.createNotification(type);
        notification.send(msg);

    }
}