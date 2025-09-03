// The problematic class with a telescoping constructor
class User {
    private final String firstName; // Required
    private final String lastName;  // Required
    private int age;                // Optional
    private String phone;           // Optional
    private String address;         // Optional
    private String email;           // Optional
    private boolean isVerified;     // Optional
    private long lastLogin;         // Optional
    
    private User(Builder build){
        this.firstName = build.firstName;
        this.lastName = build.lastName;
        this.age = build.age;
        this.phone = build.phone;
        this.address = build.address;
        this.email = build.email;
        this.isVerified = build.isVerified;
        this.lastLogin = build.lastLogin;
    }
    // Constructor 1: Minimum required fields
    // public User(String firstName, String lastName) {
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    // }

    // // Constructor 2: Adding age
    // public User(String firstName, String lastName, int age) {
    //     this(firstName, lastName);
    //     this.age = age;
    // }

    // // Constructor 3: Adding phone
    // public User(String firstName, String lastName, int age, String phone) {
    //     this(firstName, lastName, age);
    //     this.phone = phone;
    // }

    // // Constructor 4: Adding address
    // public User(String firstName, String lastName, int age, String phone, String address) {
    //     this(firstName, lastName, age, phone);
    //     this.address = address;
    // }

    // // Constructor 5: Adding email
    // public User(String firstName, String lastName, int age, String phone, String address, String email) {
    //     this(firstName, lastName, age, phone, address);
    //     this.email = email;
    // }

    // // Constructor 6: Adding isVerified
    // public User(String firstName, String lastName, int age, String phone, String address, String email, boolean isVerified) {
    //     this(firstName, lastName, age, phone, address, email);
    //     this.isVerified = isVerified;
    // }

    // // Constructor 7: Adding lastLogin
    // public User(String firstName, String lastName, int age, String phone, String address, String email, boolean isVerified, long lastLogin) {
    //     this(firstName, lastName, age, phone, address, email, isVerified);
    //     this.lastLogin = lastLogin;
    // }

    public static class Builder{
        private final String firstName; // Required
        private final String lastName;  // Required
        private int age;                // Optional
        private String phone;           // Optional
        private String address;         // Optional
        private String email;           // Optional
        private boolean isVerified;     // Optional
        private long lastLogin;         // Optional
        
        public Builder(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder isVerfied(boolean isVerfied){
            this.isVerified = isVerfied;
            return this;
        }

        public Builder lastLogin(long lastLogin){
            this.lastLogin = lastLogin;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    // Getters (no setters to keep it immutable after construction)
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public boolean isVerified() { return isVerified; }
    public long getLastLogin() { return lastLogin; }

    @Override
    public String toString() {
        return "User{" +
               "firstName='" + firstName + "'" +
               ", lastName='" + lastName + "'" +
               ", age=" + age +
               ", phone='" + phone + "'" +
               ", address='" + address + "'" +
               ", email='" + email + "'" +
               ", isVerified=" + isVerified +
               ", lastLogin=" + lastLogin +
               '}';
    }
}

// Main class to demonstrate the problem
public class test {
    public static void main(String[] args) {
        System.out.println("--- Demonstrating the Telescoping Constructor Problem ---");

        // Problem 1: Hard to read and understand which parameter is which
        //User user1 = new User("John", "Doe", 30, "123-456-7890", "123 Main St", "john.doe@example.com", true, System.currentTimeMillis());

        User user1 = new User.Builder("John","Doe")
                             .age(30)
                             .phone("123-456-7890")
                             .address("null")
                             .email("")
                             .isVerfied(true)
                             .lastLogin(System.currentTimeMillis())
                             .build();

        System.out.println("User 1: " + user1);

        // Problem 2: What if you only want to set a few optional parameters?
        // You still have to pass nulls or default values for the ones in between.
        // User user2 = new User("Jane", "Smith", 25, null, null, "jane.smith@example.com", false, 0);
        // System.out.println("User 2: " + user2);

        // Problem 3: Adding a new optional parameter means adding more constructors.
        System.out.println("\nProblem: The User class has too many constructors, making it hard to use and maintain.");
        System.out.println("Adding new optional fields requires adding even more constructors.");
    }
}
