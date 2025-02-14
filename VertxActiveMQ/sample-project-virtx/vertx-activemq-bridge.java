// JMSBridgeVerticle.java
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.amqp.AmqpClientOptions;
import io.vertx.amqp.AmqpConnection;
import io.vertx.core.json.JsonObject;

public class JMSBridgeVerticle extends AbstractVerticle {
    private static final String ACTIVEMQ_HOST = "localhost";
    private static final int ACTIVEMQ_PORT = 5672;  // AMQP port
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    public void start(Promise<Void> startPromise) {
        // Configure AMQP client options
        AmqpClientOptions options = new AmqpClientOptions()
            .setHost(ACTIVEMQ_HOST)
            .setPort(ACTIVEMQ_PORT)
            .setUsername(USERNAME)
            .setPassword(PASSWORD);

        // Create event bus consumer
        vertx.eventBus().consumer("app.messages", message -> {
            JsonObject json = (JsonObject) message.body();
            // Forward message to ActiveMQ
            sendToActiveMQ(json);
        });

        // Create AMQP client
        createAmqpConnection(options, startPromise);
    }

    private void createAmqpConnection(AmqpClientOptions options, Promise<Void> startPromise) {
        vertx.createNetClient()
            .connect(options.getPort(), options.getHost())
            .onSuccess(socket -> {
                System.out.println("Connected to ActiveMQ");
                startPromise.complete();
            })
            .onFailure(err -> {
                System.err.println("Failed to connect to ActiveMQ: " + err.getMessage());
                startPromise.fail(err);
            });
    }

    private void sendToActiveMQ(JsonObject message) {
        // Implementation of sending message to ActiveMQ
        System.out.println("Sending message to ActiveMQ: " + message.encode());
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JMSBridgeVerticle());
    }
}
