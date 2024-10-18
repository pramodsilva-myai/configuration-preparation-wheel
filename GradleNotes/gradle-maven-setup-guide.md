# Gradle and Maven Setup Guide

## 1. Install Gradle

### Windows
1. Download the binary-only zip file from [gradle.org](https://gradle.org/releases/)
2. Extract the zip file to a directory (e.g., `C:\Gradle`)
3. Add Gradle to your PATH: Add `C:\Gradle\bin` to your system's PATH variable

### macOS/Linux
1. Install SDKMAN: `curl -s "https://get.sdkman.io" | bash`
2. Install Gradle: `sdk install gradle`

## 2. Install Maven

### Windows
1. Download the binary zip file from [maven.apache.org](https://maven.apache.org/download.cgi)
2. Extract the zip file to a directory (e.g., `C:\Maven`)
3. Add Maven to your PATH: Add `C:\Maven\bin` to your system's PATH variable

### macOS/Linux
1. Using SDKMAN (if installed): `sdk install maven`
2. Or using Homebrew (macOS): `brew install maven`
3. Or using apt (Ubuntu/Debian): `sudo apt-get install maven`

## 3. Configure Gradle and Maven to Use a Single Directory Repository

### Gradle
1. Create or edit `~/.gradle/init.gradle` (Unix) or `C:\Users\<username>\.gradle\init.gradle` (Windows)
2. Add the following content:
   ```groovy
   allprojects {
       repositories {
           mavenLocal()
           maven {
               url "file:///path/to/your/repository"
           }
           mavenCentral()
       }
   }
   ```

### Maven
1. Edit `~/.m2/settings.xml` (create if it doesn't exist)
2. Add the following content:
   ```xml
   <settings>
     <localRepository>/path/to/your/repository</localRepository>
   </settings>
   ```

## 4. Optimizing Gradle and Maven

### Gradle Optimization
1. Enable the Gradle Daemon:
   Add `org.gradle.daemon=true` to `gradle.properties`

2. Increase memory allocation:
   Add `org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m` to `gradle.properties`

3. Enable parallel execution:
   Add `org.gradle.parallel=true` to `gradle.properties`

4. Use the build cache:
   Add `org.gradle.caching=true` to `gradle.properties`

5. Use the latest Gradle version:
   Update `distributionUrl` in `gradle/wrapper/gradle-wrapper.properties`

### Maven Optimization
1. Use the latest Maven version

2. Increase memory allocation:
   Set `MAVEN_OPTS=-Xmx2048m -XX:MaxPermSize=512m`

3. Use parallel builds:
   Run Maven with `-T` option, e.g., `mvn -T 4 clean install`

4. Use the Maven Daemon (experimental):
   Install `mvnd` and use it instead of `mvn`

5. Minimize plugins in your `pom.xml`

6. Use `<dependencyManagement>` to manage dependencies

## 5. Best Practices

- Keep your build scripts clean and modular
- Use consistent directory structures across projects
- Regularly update Gradle, Maven, and their plugins
- Use version control for your build scripts
- Document your build process and custom tasks
- Use CI/CD pipelines to automate builds and tests

Remember to adjust these configurations based on your specific project requirements and system capabilities.
