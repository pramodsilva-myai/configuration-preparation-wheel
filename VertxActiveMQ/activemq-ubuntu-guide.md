# Complete Guide to Apache ActiveMQ on Ubuntu

## Table of Contents
1. Prerequisites
2. Installation
3. Configuration
4. Starting and Managing ActiveMQ
5. Monitoring and Administration
6. Working with Topics and Queues
7. Common Issues and Troubleshooting
8. Best Practices

## 1. Prerequisites

Before installing ActiveMQ, ensure your system has:
- Ubuntu 20.04 or later
- Java 11 or later (OpenJDK preferred)
- At least 2GB RAM
- Minimum 4GB free disk space

Install Java if not already present:
```bash
sudo apt update
sudo apt install openjdk-11-jdk
java -version
```

## 2. Installation

### Method 1: Using APT (Recommended)
```bash
sudo apt install activemq
```

### Method 2: Manual Installation
```bash
# Download latest stable version
wget https://downloads.apache.org/activemq/5.17.4/apache-activemq-5.17.4-bin.tar.gz

# Extract the archive
tar xzf apache-activemq-5.17.4-bin.tar.gz

# Move to /opt directory
sudo mv apache-activemq-5.17.4 /opt/activemq

# Create symbolic link
sudo ln -s /opt/activemq/bin/activemq /usr/bin/activemq
```

## 3. Configuration

### Basic Configuration
Main configuration files are located at:
- `/etc/activemq/activemq.xml` (APT installation)
- `/opt/activemq/conf/activemq.xml` (Manual installation)

Essential configurations:
```xml
<broker xmlns="http://activemq.apache.org/schema/core">
    <!-- Broker name -->
    <broker brokerName="localhost">
    
    <!-- Network connectors -->
    <transportConnectors>
        <transportConnector name="openwire" uri="tcp://0.0.0.0:61616"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614"/>
    </transportConnectors>
</broker>
```

### Security Configuration
Enable authentication:
```xml
<plugins>
    <simpleAuthenticationPlugin>
        <users>
            <authenticationUser username="admin" password="admin" groups="administrators"/>
            <authenticationUser username="user" password="password" groups="users"/>
        </users>
    </simpleAuthenticationPlugin>
</plugins>
```

## 4. Starting and Managing ActiveMQ

### Service Management
```bash
# Start ActiveMQ
sudo systemctl start activemq

# Stop ActiveMQ
sudo systemctl stop activemq

# Restart ActiveMQ
sudo systemctl restart activemq

# Check status
sudo systemctl status activemq

# Enable on boot
sudo systemctl enable activemq
```

### Manual Control
```bash
# Start
sudo activemq start

# Stop
sudo activemq stop

# Restart
sudo activemq restart

# Check status
sudo activemq status
```

## 5. Monitoring and Administration

### Web Console
Access the web console at `http://localhost:8161/admin`
Default credentials:
- Username: admin
- Password: admin

### Command Line Monitoring
```bash
# View logs
tail -f /var/log/activemq/activemq.log

# Monitor connections
activemq browse

# List topics
activemq browse --amqurl tcp://localhost:61616 --viewAll
```

## 6. Working with Topics and Queues

### Creating Topics/Queues
Topics and queues are created automatically when first used, but can be predefined in configuration:

```xml
<destinations>
    <topic physicalName="MY.TOPIC"/>
    <queue physicalName="MY.QUEUE"/>
</destinations>
```

### Monitoring Messages
```bash
# Browse queue contents
activemq browse MY.QUEUE

# View message count
activemq query -QQueue=MY.QUEUE --view MessageCount

# View subscribers
activemq query -QTopic=MY.TOPIC --view ConsumerCount
```

## 7. Common Issues and Troubleshooting

### Connection Issues
1. Port conflicts
   - Solution: Check if ports are in use: `netstat -tulpn | grep LISTEN`
   - Modify port numbers in activemq.xml if needed

2. Memory problems
   - Symptom: ActiveMQ fails to start or becomes unresponsive
   - Solution: Adjust memory settings in `activemq-wrapper.conf`:
     ```
     wrapper.java.maxmemory=2048
     ```

3. Permission issues
   - Symptom: Cannot create/write to data directory
   - Solution: 
     ```bash
     sudo chown -R activemq:activemq /var/lib/activemq
     sudo chmod 755 /var/lib/activemq
     ```

4. Network connectivity
   - Symptom: Clients cannot connect
   - Solution: Check firewall settings:
     ```bash
     sudo ufw allow 61616/tcp  # OpenWire
     sudo ufw allow 8161/tcp   # Web Console
     ```

### Data Directory Issues
1. Disk space
   - Monitor space: `df -h /var/lib/activemq`
   - Clean old data: 
     ```bash
     sudo activemq purge
     ```

2. Corrupt KahaDB
   - Backup data directory
   - Delete corrupt files:
     ```bash
     sudo systemctl stop activemq
     sudo rm -rf /var/lib/activemq/data/kahadb/*
     sudo systemctl start activemq
     ```

## 8. Best Practices

1. Memory Management
   - Set appropriate memory limits
   - Monitor memory usage
   - Enable GC logging for troubleshooting

2. Security
   - Change default passwords
   - Use SSL for production
   - Implement authentication and authorization
   - Regular security audits

3. Performance
   - Use persistent messages only when necessary
   - Implement message expiry
   - Regular maintenance of message stores
   - Monitor network bandwidth

4. Backup and Recovery
   - Regular backup of configuration
   - Backup message stores
   - Document recovery procedures
   - Test recovery procedures

5. Monitoring
   - Set up alerts for:
     - Disk usage
     - Memory usage
     - Queue depth
     - Consumer count
     - Producer count
