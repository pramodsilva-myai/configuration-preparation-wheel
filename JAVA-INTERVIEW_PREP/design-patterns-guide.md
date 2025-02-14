# Design Pattern Interview Questions and Answers

## 1. Singleton Pattern
**Q: Explain Singleton pattern and implement a thread-safe version.**

**A:** Singleton ensures only one instance of a class exists and provides global access to it.

```java
public class Singleton {
    // Volatile ensures visibility across threads
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    // Double-checked locking
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// Alternative: Enum Singleton
public enum EnumSingleton {
    INSTANCE;
    
    public void doWork() {
        // Singleton behavior
    }
}
```

## 2. Factory Pattern
**Q: What is Factory pattern and when would you use it?**

**A:** Factory pattern provides an interface for creating objects but lets subclasses decide which class to instantiate.

```java
// Product interface
interface Animal {
    void makeSound();
}

// Concrete products
class Dog implements Animal {
    public void makeSound() { System.out.println("Woof!"); }
}

class Cat implements Animal {
    public void makeSound() { System.out.println("Meow!"); }
}

// Factory
class AnimalFactory {
    public Animal createAnimal(String type) {
        switch (type.toLowerCase()) {
            case "dog": return new Dog();
            case "cat": return new Cat();
            default: throw new IllegalArgumentException("Unknown animal type");
        }
    }
}
```

## 3. Builder Pattern
**Q: How does Builder pattern solve telescoping constructor problem?**

**A:** Builder pattern separates object construction from its representation and provides a clear way to create complex objects.

```java
public class Computer {
    private final String cpu;
    private final int ram;
    private final int storage;
    private final boolean hasGPU;
    
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.hasGPU = builder.hasGPU;
    }
    
    public static class Builder {
        private String cpu;
        private int ram;
        private int storage;
        private boolean hasGPU;
        
        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public Builder setRAM(int ram) {
            this.ram = ram;
            return this;
        }
        
        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGPU(boolean hasGPU) {
            this.hasGPU = hasGPU;
            return this;
        }
        
        public Computer build() {
            return new Computer(this);
        }
    }
}

// Usage
Computer computer = new Computer.Builder()
    .setCPU("Intel i7")
    .setRAM(16)
    .setStorage(512)
    .setGPU(true)
    .build();
```

## 4. Observer Pattern
**Q: Explain Observer pattern and its real-world applications.**

**A:** Observer pattern defines a one-to-many dependency between objects where when one object changes state, all its dependents are notified.

```java
// Observer interface
interface Observer {
    void update(String message);
}

// Subject interface
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Concrete Subject
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String news;
    
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }
    
    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}

// Concrete Observer
class NewsChannel implements Observer {
    private String name;
    
    public NewsChannel(String name) {
        this.name = name;
    }
    
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}
```

## 5. Strategy Pattern
**Q: What is Strategy pattern and when would you use it?**

**A:** Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable.

```java
// Strategy interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    
    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using credit card " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal account " + email);
    }
}

// Context
class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}
```

## 6. Decorator Pattern
**Q: Explain Decorator pattern with an example.**

**A:** Decorator pattern attaches additional responsibilities to an object dynamically.

```java
// Component interface
interface Coffee {
    double getCost();
    String getDescription();
}

// Concrete component
class SimpleCoffee implements Coffee {
    public double getCost() { return 1.0; }
    public String getDescription() { return "Simple coffee"; }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    public double getCost() { return decoratedCoffee.getCost(); }
    public String getDescription() { return decoratedCoffee.getDescription(); }
}

// Concrete decorators
class Milk extends CoffeeDecorator {
    public Milk(Coffee coffee) {
        super(coffee);
    }
    
    public double getCost() { return super.getCost() + 0.5; }
    public String getDescription() { return super.getDescription() + ", milk"; }
}

class Sugar extends CoffeeDecorator {
    public Sugar(Coffee coffee) {
        super(coffee);
    }
    
    public double getCost() { return super.getCost() + 0.2; }
    public String getDescription() { return super.getDescription() + ", sugar"; }
}
```

## 7. Proxy Pattern
**Q: What is Proxy pattern and what are its types?**

**A:** Proxy pattern provides a surrogate or placeholder for another object to control access to it.

```java
// Subject interface
interface Image {
    void display();
}

// Real subject
class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    private void loadFromDisk() {
        System.out.println("Loading " + filename);
    }
    
    public void display() {
        System.out.println("Displaying " + filename);
    }
}

// Proxy
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;
    
    public ProxyImage(String filename) {
        this.filename = filename;
    }
    
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```

## 8. Command Pattern
**Q: Explain Command pattern and its benefits.**

**A:** Command pattern encapsulates a request as an object, letting you parameterize clients with different requests.

```java
// Command interface
interface Command {
    void execute();
}

// Receiver
class Light {
    public void turnOn() { System.out.println("Light is on"); }
    public void turnOff() { System.out.println("Light is off"); }
}

// Concrete commands
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    public void execute() {
        light.turnOn();
    }
}

class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    public void execute() {
        light.turnOff();
    }
}

// Invoker
class RemoteControl {
    private Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    public void pressButton() {
        command.execute();
    }
}
```

## 9. Chain of Responsibility Pattern
**Q: How does Chain of Responsibility pattern work?**

**A:** Chain of Responsibility pattern creates a chain of receiver objects for a request.

```java
abstract class LogHandler {
    protected LogHandler nextHandler;
    protected int level;
    
    public void setNextHandler(LogHandler handler) {
        this.nextHandler = handler;
    }
    
    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextHandler != null) {
            nextHandler.logMessage(level, message);
        }
    }
    
    abstract protected void write(String message);
}

class ConsoleLogger extends LogHandler {
    public ConsoleLogger(int level) {
        this.level = level;
    }
    
    protected void write(String message) {
        System.out.println("Console: " + message);
    }
}

class FileLogger extends LogHandler {
    public FileLogger(int level) {
        this.level = level;
    }
    
    protected void write(String message) {
        System.out.println("File: " + message);
    }
}
```

## 10. Adapter Pattern
**Q: What is Adapter pattern and when should it be used?**

**A:** Adapter pattern converts the interface of a class into another interface clients expect.

```java
// Target interface
interface MediaPlayer {
    void play(String audioType, String filename);
}

// Adaptee interface
interface AdvancedMediaPlayer {
    void playVlc(String filename);
    void playMp4(String filename);
}

// Concrete Adaptee
class VlcPlayer implements AdvancedMediaPlayer {
    public void playVlc(String filename) {
        System.out.println("Playing vlc file: " + filename);
    }
    
    public void playMp4(String filename) {
        // Do nothing
    }
}

// Adapter
class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMusicPlayer;
    
    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer = new VlcPlayer();
        }
    }
    
    public void play(String audioType, String filename) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer.playVlc(filename);
        }
    }
}
```

## Common Interview Questions:

1. **Q: What are the main categories of design patterns?**
   **A:** Design patterns are categorized into:
   - Creational (object creation)
   - Structural (object composition)
   - Behavioral (object communication)

2. **Q: How do you choose between Factory and Abstract Factory?**
   **A:** 
   - Use Factory when dealing with a single family of products
   - Use Abstract Factory when dealing with multiple families of related products

3. **Q: What's the difference between Strategy and Command patterns?**
   **A:**
   - Strategy encapsulates interchangeable algorithms
   - Command encapsulates requests as objects

4. **Q: Can you explain the principle of "Program to an interface, not an implementation" in design patterns?**
   **A:** This principle promotes:
   - Loose coupling
   - Flexibility in changing implementations
   - Easier testing and maintenance

5. **Q: What are anti-patterns and how do they relate to design patterns?**
   **A:** Anti-patterns are:
   - Common but ineffective approaches to problems
   - Opposite of design patterns
   - Should be recognized and avoided
