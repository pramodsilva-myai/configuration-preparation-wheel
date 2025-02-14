# Top 30 Java Interview Questions and Answers

## 1. What is the difference between JDK, JRE, and JVM?
- **JDK (Java Development Kit)**: Complete development kit that includes JRE, compiler (javac), and development tools
- **JRE (Java Runtime Environment)**: Contains JVM and libraries needed to run Java applications
- **JVM (Java Virtual Machine)**: Executes Java bytecode and provides platform independence

## 2. Explain the difference between ArrayList and LinkedList.
```java
// ArrayList: Dynamic array implementation
ArrayList<String> arrayList = new ArrayList<>();  // Fast random access, slow insertions/deletions
arrayList.add("example");  // O(1) amortized

// LinkedList: Doubly-linked list implementation
LinkedList<String> linkedList = new LinkedList<>();  // Fast insertions/deletions, slow random access
linkedList.addFirst("example");  // O(1)
```
Key differences:
- ArrayList provides O(1) random access but O(n) insertions/deletions
- LinkedList provides O(1) insertions/deletions but O(n) random access
- ArrayList uses contiguous memory, LinkedList uses scattered nodes

## 3. What is the difference between `==` and `.equals()`?
```java
String str1 = new String("hello");
String str2 = new String("hello");

System.out.println(str1 == str2);        // false (compares references)
System.out.println(str1.equals(str2));    // true (compares content)
```

## 4. Explain the concept of inheritance and its types.
```java
// Single inheritance
class Animal {
    void eat() { System.out.println("eating..."); }
}
class Dog extends Animal {
    void bark() { System.out.println("barking..."); }
}

// Multiple inheritance (through interfaces)
interface Flyable {
    void fly();
}
interface Swimmable {
    void swim();
}
class Duck implements Flyable, Swimmable {
    public void fly() { System.out.println("flying..."); }
    public void swim() { System.out.println("swimming..."); }
}
```

## 5. What are the access modifiers in Java?
- **public**: Accessible everywhere
- **protected**: Accessible within package and by subclasses
- **default**: Accessible only within package
- **private**: Accessible only within class

## 6. What is the difference between final, finally, and finalize()?
```java
final class FinalClass {} // Cannot be inherited
final int constant = 100; // Cannot be modified

try {
    // Some code
} finally {
    // Always executes
}

class Example {
    protected void finalize() {
        // Called by garbage collector before object destruction
    }
}
```

## 7. Explain method overloading and overriding.
```java
class Calculator {
    // Method overloading (same name, different parameters)
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }
}

class Animal {
    void makeSound() { System.out.println("Some sound"); }
}
class Cat extends Animal {
    // Method overriding (same signature, different implementation)
    @Override
    void makeSound() { System.out.println("Meow"); }
}
```

## 8. What is the difference between Checked and Unchecked exceptions?
```java
// Checked Exception (must be handled or declared)
class CustomCheckedException extends Exception {
    CustomCheckedException(String message) {
        super(message);
    }
}

// Unchecked Exception (runtime)
class CustomUncheckedException extends RuntimeException {
    CustomUncheckedException(String message) {
        super(message);
    }
}
```

## 9. Explain the concept of abstraction.
```java
abstract class Shape {
    abstract double getArea();
    void display() { System.out.println("This is a shape"); }
}

class Circle extends Shape {
    double radius;
    @Override
    double getArea() { return Math.PI * radius * radius; }
}
```

## 10. What is the difference between interface and abstract class?
```java
interface Vehicle {
    void start();  // implicitly public abstract
    default void stop() { System.out.println("Stopping"); }
}

abstract class AbstractVehicle {
    abstract void accelerate();
    void brake() { System.out.println("Braking"); }
}
```

## 11. Explain the concept of Multithreading.
```java
// Using Thread class
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}

// Using Runnable interface
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable running");
    }
}
```

## 12. What are collections in Java?
```java
// List example
List<String> list = new ArrayList<>();
list.add("item");

// Set example
Set<Integer> set = new HashSet<>();
set.add(1);

// Map example
Map<String, Integer> map = new HashMap<>();
map.put("key", 1);
```

## 13. Explain the concept of Singleton pattern.
```java
public class Singleton {
    private static Singleton instance;
    private Singleton() {}
    
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

## 14. What is garbage collection in Java?
- Automatic memory management
- Removes unreferenced objects
- Can be requested using `System.gc()` but not guaranteed
- Uses different algorithms (Serial, Parallel, G1)

## 15. Explain the concept of String immutability.
```java
String str = "hello";
str = str + " world";  // Creates new String object
StringBuilder sb = new StringBuilder("hello");
sb.append(" world");   // Modifies existing object
```

## 16. What is the difference between StringBuilder and StringBuffer?
```java
// StringBuffer is thread-safe (synchronized)
StringBuffer buffer = new StringBuffer();
buffer.append("thread").append(" safe");

// StringBuilder is not thread-safe but faster
StringBuilder builder = new StringBuilder();
builder.append("faster").append(" execution");
```

## 17. Explain the concept of polymorphism.
```java
class Animal {
    void makeSound() { System.out.println("Some sound"); }
}

class Dog extends Animal {
    void makeSound() { System.out.println("Woof"); }
}

Animal animal = new Dog();  // Runtime polymorphism
animal.makeSound();  // Outputs: Woof
```

## 18. What are generics in Java?
```java
// Generic class
class Box<T> {
    private T content;
    public void set(T content) { this.content = content; }
    public T get() { return content; }
}

// Generic method
public <T> void printArray(T[] array) {
    for (T item : array) {
        System.out.println(item);
    }
}
```

## 19. Explain the concept of serialization.
```java
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private transient int age;  // Won't be serialized
}
```

## 20. What is the difference between HashMap and HashTable?
```java
// HashMap (not thread-safe, allows null)
HashMap<String, Integer> map = new HashMap<>();
map.put(null, null);  // Allowed

// Hashtable (thread-safe, doesn't allow null)
Hashtable<String, Integer> table = new Hashtable<>();
// table.put(null, null);  // Throws NullPointerException
```

## 21. Explain Exception Handling.
```java
try {
    // Risky code
    throw new Exception("Error");
} catch (Exception e) {
    // Handle exception
    e.printStackTrace();
} finally {
    // Always executes
    System.out.println("Cleanup");
}
```

## 22. What is the difference between local and instance variables?
```java
public class Example {
    private int instanceVar;  // Instance variable
    
    public void method() {
        int localVar = 0;  // Local variable
    }
}
```

## 23. Explain the concept of inner classes.
```java
class Outer {
    private int x = 10;
    
    class Inner {
        void print() { System.out.println(x); }
    }
    
    static class StaticInner {
        void print() { System.out.println("Static inner"); }
    }
}
```

## 24. What are Java 8 features?
```java
// Lambda expressions
List<Integer> numbers = Arrays.asList(1, 2, 3);
numbers.forEach(n -> System.out.println(n));

// Stream API
List<Integer> doubled = numbers.stream()
                             .map(n -> n * 2)
                             .collect(Collectors.toList());

// Optional
Optional<String> optional = Optional.of("value");
```

## 25. Explain the concept of method references.
```java
List<String> list = Arrays.asList("a", "b", "c");
list.forEach(System.out::println);  // Method reference
```

## 26. What is synchronization in Java?
```java
public class Counter {
    private int count;
    
    synchronized void increment() {
        count++;
    }
    
    void incrementBlock() {
        synchronized(this) {
            count++;
        }
    }
}
```

## 27. Explain the concept of functional interfaces.
```java
@FunctionalInterface
interface Calculator {
    int calculate(int x, int y);
}

// Lambda expression implementation
Calculator add = (x, y) -> x + y;
```

## 28. What are Java Streams?
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

int sum = numbers.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum();
```

## 29. Explain the concept of dependency injection.
```java
class Service {
    private Repository repository;
    
    // Constructor injection
    public Service(Repository repository) {
        this.repository = repository;
    }
}
```

## 30. What are annotations in Java?
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation {
    String value() default "";
}

class Example {
    @CustomAnnotation("test")
    void method() {}
}
```
