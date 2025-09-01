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
}