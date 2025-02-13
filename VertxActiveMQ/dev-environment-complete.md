# Complete Development Environment Setup Guide

## System Preparation
```bash
sudo apt update
sudo apt upgrade
```

## Java Installation & Configuration

### Java Versions
```bash
# Latest Version
sudo apt install default-jdk

# Specific Versions
sudo apt install openjdk-8-jdk
sudo apt install openjdk-11-jdk
sudo apt install openjdk-17-jdk
sudo apt install openjdk-21-jdk
```

### Java Locations
- JDK installations: `/usr/lib/jvm/`
- Latest version symlink: `/usr/lib/jvm/default-java`
- Version-specific paths:
  - `/usr/lib/jvm/java-8-openjdk-amd64`
  - `/usr/lib/jvm/java-11-openjdk-amd64`
  - `/usr/lib/jvm/java-17-openjdk-amd64`
  - `/usr/lib/jvm/java-21-openjdk-amd64`

### Java Environment Variables
```bash
# Add to ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin
```

## Maven Installation & Configuration

### Maven Versions
```bash
# Latest Version
sudo apt install maven

# Specific Version (example: 3.9.6)
wget https://downloads.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
tar -xzvf apache-maven-3.9.6-bin.tar.gz
sudo mv apache-maven-3.9.6 /opt/maven
```

### Maven Locations
- APT installation: `/usr/share/maven`
- Manual installation: `/opt/maven`
- Local repository: `~/.m2/repository`
- Configuration: `~/.m2/settings.xml`

### Maven Environment Variables
```bash
# Add to ~/.bashrc
export M2_HOME=/opt/maven/apache-maven-3.9.6
export PATH=$PATH:$M2_HOME/bin
```

## Gradle Installation & Configuration

### Gradle Versions
```bash
# Latest Version
sudo apt install gradle

# Specific Version (example: 8.5)
wget https://services.gradle.org/distributions/gradle-8.5-bin.zip
unzip gradle-8.5-bin.zip
sudo mv gradle-8.5 /opt/gradle
```

### Gradle Locations
- APT installation: `/usr/share/gradle`
- Manual installation: `/opt/gradle`
- User home: `~/.gradle`
- Project-specific: `project-directory/.gradle`

### Gradle Environment Variables
```bash
# Add to ~/.bashrc
export GRADLE_HOME=/opt/gradle/gradle-8.5
export PATH=$PATH:$GRADLE_HOME/bin
```

## Git Installation & Configuration

### Git Versions
```bash
# Latest Version
sudo apt install git

# Latest Stable from PPA
sudo add-apt-repository ppa:git-core/ppa
sudo apt update
sudo apt install git
```

### Git Locations
- Executable: `/usr/bin/git`
- System config: `/etc/gitconfig`
- User config: `~/.gitconfig`
- Project config: `project-directory/.git/config`

### Git Basic Configuration
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
git config --global core.editor "nano"
git config --global init.defaultBranch main
```

## Environment Variable Management

### Dynamic Version Management
```bash
# Add to ~/.bashrc
# Java
if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
else
    export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:/bin/java::")
fi

# Maven
if [ -d "/opt/maven/apache-maven-3.9.6" ]; then
    export M2_HOME=/opt/maven/apache-maven-3.9.6
else
    export M2_HOME=/usr/share/maven
fi

# Gradle
if [ -d "/opt/gradle/gradle-8.5" ]; then
    export GRADLE_HOME=/opt/gradle/gradle-8.5
else
    export GRADLE_HOME=/usr/share/gradle
fi

# PATH
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin:$GRADLE_HOME/bin
```

## Summary Table

| Tool | Latest Version Install | Specific Version Install | Main Configuration Location | Library Location | Environment Variable |
|------|----------------------|-------------------------|---------------------------|------------------|-------------------|
| Java | `sudo apt install default-jdk` | `sudo apt install openjdk-{version}-jdk` | N/A | `/usr/lib/jvm/` | `JAVA_HOME=/usr/lib/jvm/java-{version}` |
| Maven | `sudo apt install maven` | Download from Apache website | `~/.m2/settings.xml` | `/usr/share/maven` or `/opt/maven` | `M2_HOME=/opt/maven/apache-maven-{version}` |
| Gradle | `sudo apt install gradle` | Download from Gradle website | `~/.gradle/gradle.properties` | `/opt/gradle` | `GRADLE_HOME=/opt/gradle/gradle-{version}` |
| Git | `sudo apt install git` | Via PPA or apt | `~/.gitconfig` | `/usr/bin/git` | N/A |

## Version Verification Commands
```bash
# Check all versions
java -version
mvn -version
gradle -version
git --version

# Check paths
which java
which mvn
which gradle
which git
```

## Common Library Paths and Their Uses

| Path | Description | Used By |
|------|-------------|---------|
| `/usr/lib/jvm/` | Java installations | JDK/JRE |
| `~/.m2/repository` | Maven local cache | Maven projects |
| `~/.gradle` | Gradle user home | Gradle projects |
| `~/.git` | Git user settings | Git |
| `/etc/alternatives` | System symlinks | All tools |

## Quick Environment Refresh
```bash
# After making changes to ~/.bashrc
source ~/.bashrc

# Verify all paths
echo $JAVA_HOME
echo $M2_HOME
echo $GRADLE_HOME
echo $PATH
```
