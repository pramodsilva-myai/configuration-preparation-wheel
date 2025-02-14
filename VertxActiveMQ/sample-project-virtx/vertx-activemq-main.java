// MainApplication.java
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MainApplication {
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "app.queue";

    public static void main(String[] args) {
        // Deploy the bridge verticle
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JMSBridgeVerticle(), ar -> {
            if (ar.succeeded()) {
                setupJMSConsumer();
                // Send a test message
                sendTestMessage(vertx);
            } else {
                System.err.println("Failed to deploy verticle: " + ar.cause());
            }
        });
    }

    private static void setupJMSConsumer() {
        try {
            // Create JMS connection factory
            ConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Create destination (Queue)
            Destination destination = session.createQueue(QUEUE_NAME);
            
            // Create consumer
            MessageConsumer consumer = session.createConsumer(destination);
            
            // Set message listener
            consumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Received message: " + text);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            
            // Start connection
            connection.start();
            System.out.println("JMS Consumer started");
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static void sendTestMessage(Vertx vertx) {
        JsonObject testMessage = new JsonObject()
            .put("message", "Hello from Vert.x!")
            .put("timestamp", System.currentTimeMillis());
        
        vertx.eventBus().publish("app.messages", testMessage);
    }
}
