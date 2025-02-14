# 20 Advanced Java Interview Questions and Answers

## 1. Explain Concurrent Collections in Java
```java
// ConcurrentHashMap example
ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
concurrentMap.put("key", 1);
concurrentMap.putIfAbsent("key", 2);

// CopyOnWriteArrayList for thread-safe iteration
CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
copyOnWriteList.add("item");
for (String item : copyOnWriteList) {  // Safe iteration while modifications occur
    System.out.println(item);
}
```

## 2. How does CompletableFuture work?
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Async computation
    return "Result";
}).thenApply(result -> {
    // Transform result
    return result.toUpperCase();
}).thenAccept(System.out::println);

// Combining futures
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");
CompletableFuture<String> combined = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);
```

## 3. Explain Java Memory Model and Happens-Before Relationship
```java
class MemoryExample {
    private volatile boolean flag = false;
    private int data = 0;
    
    public void writer() {
        data = 42;    // Write to data
        flag = true;  // Volatile write
    }
    
    public void reader() {
        if (flag) {   // Volatile read
            // data is guaranteed to be 42 here
            System.out.println(data);
        }
    }
}
```

## 4. How do you implement Custom Thread Pool?
```java
public class CustomThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> threads;
    
    public CustomThreadPool(int poolSize) {
        taskQueue = new LinkedBlockingQueue<>();
        threads = new ArrayList<>(poolSize);
        
        for (int i = 0; i < poolSize; i++) {
            WorkerThread thread = new WorkerThread();
            thread.start();
            threads.add(thread);
        }
    }
    
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
    
    public void execute(Runnable task) {
        taskQueue.offer(task);
    }
}
```

## 5. Explain Different Types of References in Java
```java
public class ReferenceExample {
    public void demonstrateReferences() {
        // Strong reference
        StringBuilder strong = new StringBuilder();
        
        // Weak reference
        WeakReference<StringBuilder> weak = new WeakReference<>(new StringBuilder());
        
        // Soft reference
        SoftReference<StringBuilder> soft = new SoftReference<>(new StringBuilder());
        
        // Phantom reference
        ReferenceQueue<StringBuilder> queue = new ReferenceQueue<>();
        PhantomReference<StringBuilder> phantom = 
            new PhantomReference<>(new StringBuilder(), queue);
    }
}
```

## 6. How to Implement Custom Annotation Processor?
```java
@SupportedAnnotationTypes("com.example.CustomAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class CustomProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, 
                         RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = 
                roundEnv.getElementsAnnotatedWith(annotation);
            // Process elements
        }
        return true;
    }
}
```

## 7. Explain Fork/Join Framework
```java
public class SumTask extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    private final int threshold = 10_000;
    
    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= threshold) {
            return computeDirectly();
        }
        
        int middle = start + length / 2;
        SumTask leftTask = new SumTask(numbers, start, middle);
        SumTask rightTask = new SumTask(numbers, middle, end);
        
        leftTask.fork();
        long rightResult = rightTask.compute();
        long leftResult = leftTask.join();
        
        return leftResult + rightResult;
    }
}
```

## 8. How to Implement Custom ClassLoader?
```java
public class CustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        return defineClass(name, classData, 0, classData.length);
    }
    
    private byte[] loadClassData(String name) {
        // Load the class file data
        // Return the bytes
        return new byte[0];
    }
}
```

## 9. Explain Java 9 Module System
```java
// module-info.java
module com.example.mymodule {
    requires java.base;
    requires java.sql;
    
    exports com.example.mymodule.api;
    provides com.example.mymodule.api.Service 
        with com.example.mymodule.impl.ServiceImpl;
}
```

## 10. Implement Custom Lock with Conditions
```java
public class CustomLock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;
    
    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockedBy != currentThread) {
            wait();
        }
        isLocked = true;
        lockCount++;
        lockedBy = currentThread;
    }
    
    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;
            if (lockCount == 0) {
                isLocked = false;
                lockedBy = null;
                notify();
            }
        }
    }
}
```

## 11. Explain Java NIO and Channel API
```java
public class NIOExample {
    public void readFile(String path) throws IOException {
        FileChannel channel = FileChannel.open(
            Paths.get(path), StandardOpenOption.READ);
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
        }
    }
}
```

## 12. Implement Custom Collection
```java
public class CustomList<E> implements List<E> {
    private Object[] elements;
    private int size;
    
    public CustomList() {
        elements = new Object[10];
    }
    
    @Override
    public boolean add(E e) {
        ensureCapacity();
        elements[size++] = e;
        return true;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        return (E) elements[index];
    }
    
    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }
    }
    // Other List methods...
}
```

## 13. Explain Java Memory Leaks and Prevention
```java
public class MemoryLeakExample {
    // Potential memory leak - static collection
    private static final List<Object> leakyList = new ArrayList<>();
    
    // Prevention - use WeakHashMap for caching
    private final Map<Key, Value> cache = new WeakHashMap<>();
    
    // Prevention - clean up resources
    public void processData() {
        try (InputStream is = new FileInputStream("file.txt")) {
            // Process data
        } catch (IOException e) {
            // Handle exception
        }
    }
}
```

## 14. Implement Custom Executor Service
```java
public class CustomExecutor implements ExecutorService {
    private final ThreadPoolExecutor executor;
    
    public CustomExecutor(int corePoolSize, int maxPoolSize) {
        executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());
    }
    
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        RunnableFuture<T> futureTask = new FutureTask<>(task);
        executor.execute(futureTask);
        return futureTask;
    }
    // Other ExecutorService methods...
}
```

## 15. Explain Java Security Manager
```java
public class CustomSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        if (perm instanceof FilePermission) {
            if (perm.getActions().equals("write")) {
                throw new SecurityException("Write not allowed");
            }
        }
        // Allow other permissions
    }
}
```

## 16. Implement Custom Serialization
```java
public class CustomSerialization implements Serializable {
    private transient SecretKey secretKey;
    private String encryptedData;
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // Custom serialization logic
        byte[] keyBytes = secretKey.getEncoded();
        out.writeInt(keyBytes.length);
        out.write(keyBytes);
    }
    
    private void readObject(ObjectInputStream in) 
        throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Custom deserialization logic
        int length = in.readInt();
        byte[] keyBytes = new byte[length];
        in.readFully(keyBytes);
        // Reconstruct secret key
    }
}
```

## 17. Explain Java Reflection API
```java
public class ReflectionExample {
    public void demonstrateReflection() throws Exception {
        Class<?> clazz = Class.forName("com.example.MyClass");
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object instance = constructor.newInstance();
        
        Method method = clazz.getDeclaredMethod("privateMethod");
        method.setAccessible(true);
        method.invoke(instance);
        
        Field field = clazz.getDeclaredField("privateField");
        field.setAccessible(true);
        field.set(instance, "new value");
    }
}
```

## 18. Implement Custom Annotation with Runtime Processing
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Cacheable {
    String key() default "";
    long timeToLive() default 3600;
}

public class CacheAspect {
    public Object processCacheableMethod(ProceedingJoinPoint joinPoint) 
        throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        if (cacheable != null) {
            // Check cache and return cached value if exists
            // Otherwise proceed and cache the result
        }
        return joinPoint.proceed();
    }
}
```

## 19. Explain Java Agent and Instrumentation
```java
public class CustomAgent {
    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer((loader, className, classBeingRedefined,
                            protectionDomain, classfileBuffer) -> {
            // Modify class bytes
            return classfileBuffer;
        });
    }
}
```

## 20. Implement Custom Connection Pool
```java
public class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final List<Connection> allConnections;
    
    public ConnectionPool(int poolSize) throws SQLException {
        pool = new ArrayBlockingQueue<>(poolSize);
        allConnections = new ArrayList<>(poolSize);
        
        for (int i = 0; i < poolSize; i++) {
            Connection conn = createConnection();
            pool.offer(conn);
            allConnections.add(conn);
        }
    }
    
    public Connection getConnection() throws InterruptedException {
        return pool.take();
    }
    
    public void releaseConnection(Connection conn) {
        pool.offer(conn);
    }
    
    private Connection createConnection() throws SQLException {
        // Create and return database connection
        return DriverManager.getConnection("jdbc:url");
    }
}
```
