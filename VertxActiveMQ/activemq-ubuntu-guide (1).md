# ActiveMQ Installation and Management Guide for Ubuntu

## Installation Methods

### Method 1: Using APT Repository
```bash
# Update package lists
sudo apt update

# Install ActiveMQ
sudo apt install activemq
```

### Method 2: Manual Installation from Apache Archive
```bash
# Download ActiveMQ (adjust version as needed)
wget https://downloads.apache.org/activemq/5.18.3/apache-activemq-5.18.3-bin.tar.gz

# Extract the archive
tar -xvf apache-activemq-5.18.3-bin.tar.gz

# Move to /opt directory
sudo mv apache-activemq-5.18.3 /opt/activemq

# Create symbolic link
sudo ln -s /opt/activemq/bin/activemq /usr/bin/activemq
```

## Service Management

### Using SystemD (APT Installation)
```bash
# Start ActiveMQ
sudo systemctl start activemq

# Stop ActiveMQ
sudo systemctl stop activemq

# Restart ActiveMQ
sudo systemctl restart activemq

# Check status
sudo systemctl status activemq

# Enable at boot
sudo systemctl enable activemq
```

### Using Binary Scripts (Manual Installation)
```bash
# Start ActiveMQ
sudo /opt/activemq/bin/activemq start

# Stop ActiveMQ
sudo /opt/activemq/bin/activemq stop

# Restart ActiveMQ
sudo /opt/activemq/bin/activemq restart

# Check status
sudo /opt/activemq/bin/activemq status
```

## Monitoring and Statistics

### Web Console Access
- Default URL: http://localhost:8161/admin
- Default credentials: admin/admin
- Access through browser to view:
  - Queue statistics
  - Topic information
  - Connection details
  - Message counts

### Command Line Statistics
```bash
# View all queues
activemq browse

# View specific queue
activemq browse --amqurl tcp://localhost:61616 QUEUE_NAME

# Display connection statistics
activemq connection-stats

# Show broker statistics
activemq broker-stats
```

## Configuration Locations

### APT Installation
- Main configuration: `/etc/activemq/activemq.xml`
- Environment settings: `/etc/default/activemq`
- Log files: `/var/log/activemq`
- Data directory: `/var/lib/activemq`

### Manual Installation
- Main configuration: `/opt/activemq/conf/activemq.xml`
- Environment settings: `/opt/activemq/bin/env`
- Log files: `/opt/activemq/data/`
- Data directory: `/opt/activemq/data/`

## Port Configuration
Default ports:
- 61616: JMS
- 8161: Web Console
- 5672: AMQP
- 61613: STOMP
- 1883: MQTT

To change ports, modify the corresponding transport connectors in activemq.xml:
```xml
<transportConnectors>
    <transportConnector name="openwire" uri="tcp://0.0.0.0:61616"/>
    <transportConnector name="amqp" uri="amqp://0.0.0.0:5672"/>
    <transportConnector name="stomp" uri="stomp://0.0.0.0:61613"/>
    <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883"/>
</transportConnectors>
```

## Common Troubleshooting Commands
```bash
# Check if ActiveMQ process is running
ps aux | grep activemq

# Check port usage
sudo netstat -tulpn | grep java

# View real-time logs
tail -f /var/log/activemq/activemq.log

# Check memory usage
ps aux | grep activemq | awk '{print $4}'

# View open files and ports
sudo lsof -i -P -n | grep java
```
