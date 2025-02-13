# JAR Packaging and Running Instructions

## Building Options

### Fat JAR (All-in-one JAR)
Build a fat JAR containing all dependencies:
```bash
mvn clean package -P fat-jar
```

The fat JAR will be created at: `target/jar-packaging-fat.jar`

### Thin JAR (Separate dependencies)
Build a thin JAR with dependencies in a separate directory:
```bash
mvn clean package -P thin-jar
```

This creates:
- Main JAR: `target/jar-packaging-1.0-SNAPSHOT.jar`
- Dependencies: `target/lib/`

## Running Options

### Running Fat JAR
```bash
# Simple execution
java -jar target/jar-packaging-fat.jar

# With custom JVM options
java -Xms512m -Xmx1g -jar target/jar-packaging-fat.jar

# With system properties
java -Dserver.port=8080 -jar target/jar-packaging-fat.jar
```

### Running Thin JAR
```bash
# Using classpath
java -cp "target/jar-packaging-1.0-SNAPSHOT.jar:target/lib/*" com.example.MainVerticle

# Using JAR directly (if manifest is properly configured)
java -jar target/jar-packaging-1.0-SNAPSHOT.jar
```

### Development Mode
```bash
# Run with class files
java -cp target/classes:target/lib/* com.example.MainVerticle

# Run with debugging enabled
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar target/jar-packaging-fat.jar
```

## Common Issues and Solutions

1. **ClassNotFoundException**
   - Check if all dependencies are in the lib directory
   - Verify the manifest Class-Path entries
   - Try using the fat JAR instead

2. **NoClassDefFoundError**
   - Ensure you're using the correct Java version
   - Verify all dependencies are included
   - Check for version conflicts

3. **Missing Main Class**
   - Verify the main class is correctly specified in the manifest
   - Check the package name and class name match exactly

## Best Practices

1. Always use version control (git) for your project
2. Keep the main class in a consistent location
3. Use profiles to manage different packaging options
4. Include necessary resources in the JAR
5. Test both packaging types before deployment

## Directory Structure
```
target/
├── classes/                    # Compiled classes
├── lib/                       # Dependencies (thin JAR)
├── jar-packaging-fat.jar      # Fat JAR
└── jar-packaging-1.0-SNAPSHOT.jar  # Thin JAR
```
