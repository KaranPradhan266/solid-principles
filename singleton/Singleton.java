/* Singleton Pattern is a creational design pattern that guarantees a class has only one instance and provides a global point of access to it.
In Java, one of the ways to implement Singleton is by making the constructor private and providing a static method for external objects to access it.*/

/* There are several ways to implement the Singleton Pattern in Java, each with its own advantages and disadvantages. */

/* 1. Lazy Initialization:
      This approach creates the singleton instance only when it is needed, saving resources if the singleton is never used in the application.*/
// Checks if an instance already exists (instance == null).
// If not, it creates a new instance.
// If an instance already exists, it skips the creation step.

/*Warning:
This implementation is not thread-safe. If multiple threads call getInstance() simultaneously when instance is null, it's possible to create multiple instances.
We can make it Thread safe, by making the method synchronized, so that other thread must wait until method gets executed, but doing so can cause substantial overhead
and reduce performance, which can bottleneck if called frequently.*/

import javax.management.RuntimeErrorException;

class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if(instance == null) instance = new LazySingleton();
        
        return instance;
    }
};

/* 3. Double-Checked Locking
This approach minimizes performance overhead from synchronization by only synchronizing when the object is first created.

It uses the volatile keyword to ensure that changes to the instance variable are immediately visible to other threads. */
// If the first check (instance == null) passes, we synchronize on the class object.
// We check the same condition one more time because multiple threads may have passed the first check.
// The instance is created only if both checks pass.
class DoubleCheckingSingleton {
    private static volatile DoubleCheckingSingleton instance;

    private DoubleCheckingSingleton(){}

    public static DoubleCheckingSingleton getInstance(){
        if(instance == null){
            synchronized (DoubleCheckingSingleton.class){
                if(instance == null){
                    instance = new DoubleCheckingSingleton();
                }
            }
        }
        
        return instance;
    }
};

/*4. Eager Initialization
In this method, we rely on the JVM to create the singleton instance when the class is loaded. The JVM guarantees that the instance will be created before any thread access the instance variable.

This implementation is one of the simplest and inherently thread-safe without needing explicit synchronization. */
class EagerInit {
    private static final EagerInit instance = new EagerInit();

    private EagerInit(){}

    public static EagerInit getinstance(){
        return instance;
    }
};

/*5. Bill Pugh Singleton
This implementation uses a static inner helper class to hold the singleton instance. The inner class is not loaded into memory until it's referenced for the first time in the getInstance() method.

It is thread-safe without requiring explicit synchronization.  */
class BPSingleton {
    private BPSingleton(){}

    private static class SingletonHelper {
        private static final BPSingleton instance = new BPSingleton();
    }

    public static BPSingleton getinstance(){
        return SingletonHelper.instance;
    }
};
/*Static Block Initialization
This is similar to eager initialization, but the instance is created in a static block.

It provides the ability to handle exceptions during instance creation, which is not possible with simple eager initialization. */
class StaticBlockSingleton {
    private static StaticBlockSingleton instance;

    private StaticBlockSingleton(){}

    static {
        try{
            instance = new StaticBlockSingleton();
        }catch(Exception e){
            throw new RuntimeException("Exception occured while creating singleton instance");
        }
    }

    public static StaticBlockSingleton getinstance(){
        return instance;
    }
};

/*Enum Singleton
In this method, the singleton is declared as an enum rather than a class.

Java ensures that only one instance of an enum value is created, even in a multithreaded environment.

The Enum Singleton pattern is the most robust and concise way to implement a singleton in Java. */
enum EnumSingleton {
    INSTANCE;

    public void doSomething(){
        
    }
}