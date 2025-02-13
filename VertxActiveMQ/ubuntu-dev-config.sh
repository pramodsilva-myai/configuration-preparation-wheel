#!/bin/bash

# Ubuntu Development Environment Setup Script
# Save this as 'dev-setup.sh' and run with: bash dev-setup.sh

# Update system
sudo apt update && sudo apt upgrade -y

# Install development tools
sudo apt install -y openjdk-17-jdk git maven gradle

# Create necessary directories
sudo mkdir -p /opt/maven
sudo mkdir -p /opt/gradle

# Set up environment variables
cat << 'EOF' > ~/.bashrc
# Default editor
export EDITOR=nano

# Java
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin

# Maven
export M2_HOME=/opt/maven
export PATH=$PATH:$M2_HOME/bin

# Gradle
export GRADLE_HOME=/opt/gradle
export PATH=$PATH:$GRADLE_HOME/bin

# Custom aliases for development
alias mvnc='mvn clean'
alias mvni='mvn install'
alias mvnci='mvn clean install'
alias mvnd='mvn deploy'
alias mvnt='mvn test'
alias gw='./gradlew'
alias gwb='./gradlew build'
alias gwc='./gradlew clean'
alias gwr='./gradlew run'

# Git aliases
alias gs='git status'
alias gc='git commit'
alias gp='git pull'
alias gpu='git push'
alias gb='git branch'
alias gco='git checkout'
alias gl='git log --oneline'
EOF

# Create Maven settings file
mkdir -p ~/.m2
cat << 'EOF' > ~/.m2/settings.xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                             http://maven.apache.org/xsd/settings-1.0.0.xsd">
    
    <localRepository>${user.home}/.m2/repository</localRepository>
    
    <servers>
        <server>
            <id>central</id>
            <configuration>
                <httpConfiguration>
                    <all>
                        <useSystemProperties>true</useSystemProperties>
                    </all>
                </httpConfiguration>
            </configuration>
        </server>
    </servers>
    
    <profiles>
        <profile>
            <id>default</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <maven.test.failure.ignore>true</maven.test.failure.ignore>
            </properties>
        </profile>
    </profiles>
</settings>
EOF

# Create Gradle properties
mkdir -p ~/.gradle
cat << 'EOF' > ~/.gradle/gradle.properties
org.gradle.daemon=true
org.gradle.jvmargs=-Xmx2048m
org.gradle.parallel=true
org.gradle.configureondemand=true
EOF

# Set correct permissions
sudo chown -R $USER:$USER /opt/maven
sudo chown -R $USER:$USER /opt/gradle
sudo chmod +x /opt/maven/bin/* 2>/dev/null || true
sudo chmod +x /opt/gradle/bin/* 2>/dev/null || true

# Create a verification script
cat << 'EOF' > ~/verify-dev-env.sh
#!/bin/bash

echo "Verifying Development Environment Setup..."
echo "----------------------------------------"

echo "Java Version:"
java -version

echo -e "\nMaven Version:"
mvn -version

echo -e "\nGradle Version:"
gradle -version

echo -e "\nGit Version:"
git --version

echo -e "\nEnvironment Variables:"
echo "JAVA_HOME=$JAVA_HOME"
echo "M2_HOME=$M2_HOME"
echo "GRADLE_HOME=$GRADLE_HOME"

echo -e "\nVerifying Paths:"
which java
which mvn
which gradle
which git
EOF

chmod +x ~/verify-dev-env.sh

# Source the new configuration
source ~/.bashrc

echo "Setup complete! Run '~/verify-dev-env.sh' to verify your development environment."
