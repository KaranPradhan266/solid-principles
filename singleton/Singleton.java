/* Singleton Pattern is a creational design pattern that guarantees a class has only one instance and provides a global point of access to it.
In Java, one of the ways to implement Singleton is by making the constructor private and providing a static method for external objects to access it.*/

/* There are several ways to implement the Singleton Pattern in Java, each with its own advantages and disadvantages. */

/* 1. Lazy Initialization:
      This approach creates the singleton instance only when it is needed, saving resources if the singleton is never used in the application.*/
// Checks if an instance already exists (instance == null).
// If not, it creates a new instance.
// If an instance already exists, it skips the creation step.

/*Warning:
This implementation is not thread-safe. If multiple threads call getInstance() simultaneously when instance is null, it's possible to create multiple instances.*/
class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if(instance == null) instance = new LazySingleton();
        
        return instance;
    }
};
