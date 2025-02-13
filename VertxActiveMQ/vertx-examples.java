// First, let's define the Maven dependencies needed

// pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.example</groupId>
    <artifactId>vertx-examples</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <properties>
        <vertx.version>4.5.1</vertx.version>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Core Vert.x -->
        <dependency>
            <groupId>io.vertx</groupId>
            <artifactId>vertx-core</artifactId>
            <version>${vertx.version}</version>
        </dependency>
        
        <!-- Web Client -->
        <dependency>
            <groupId>io.vertx</groupId>
            <artifactId>vertx-web-client</artifactId>
            <version>${vertx.version}</version>
        </dependency>
        
        <!-- Web (Router) -->
        <dependency>
            <groupId>io.vertx</groupId>
            <artifactId>vertx-web</artifactId>
            <version>${vertx.version}</version>
        </dependency>
    </dependencies>
</project>

// 1. HTTP Server Example
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class HttpServerExample extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        // Create a Router
        Router router = Router.router(vertx);
        
        // Enable body handling
        router.route().handler(BodyHandler.create());
        
        // GET endpoint
        router.get("/api/hello").handler(ctx -> {
            ctx.response()
                .putHeader("content-type", "application/json")
                .end("{\"message\": \"Hello from Vert.x!\"}");
        });
        
        // POST endpoint
        router.post("/api/data").handler(ctx -> {
            // Get request body
            String body = ctx.getBodyAsString();
            
            // Send response
            ctx.response()
                .putHeader("content-type", "application/json")
                .end("{\"received\": " + body + "}");
        });
        
        // Start HTTP server
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port 8080");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }
}

// 2. Web Client Example
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.core.buffer.Buffer;

public class WebClientExample extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        // Create Web Client
        WebClient client = WebClient.create(vertx);
        
        // GET request example
        client.get(8080, "localhost", "/api/hello")
            .send()
            .onSuccess(response -> {
                System.out.println("Received response: " + response.bodyAsString());
            })
            .onFailure(err -> {
                System.out.println("Error: " + err.getMessage());
            });
            
        // POST request example
        JsonObject data = new JsonObject()
            .put("name", "John")
            .put("age", 30);
            
        client.post(8080, "localhost", "/api/data")
            .sendJsonObject(data)
            .onSuccess(response -> {
                System.out.println("POST response: " + response.bodyAsString());
            })
            .onFailure(err -> {
                System.out.println("Error: " + err.getMessage());
            });
    }
}

// 3. Event Bus Example
public class EventBusExample extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        // Register a consumer
        vertx.eventBus().<String>consumer("news.uk", message -> {
            System.out.println("Received news: " + message.body());
            
            // Reply to the message
            message.reply("News received!");
        });
        
        // Send a message
        vertx.eventBus().send("news.uk", "Breaking news!", ar -> {
            if (ar.succeeded()) {
                System.out.println("News delivered: " + ar.result().body());
            } else {
                System.out.println("News delivery failed");
            }
        });
        
        // Publish a message to all consumers
        vertx.eventBus().publish("news.uk", "Broadcast news!");
    }
}

// 4. Periodic Task Example
public class PeriodicExample extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) {
        // Simple periodic task
        vertx.setPeriodic(1000, id -> {
            System.out.println("Timer fired!");
        });
        
        // Periodic task with EventBus
        vertx.setPeriodic(5000, id -> {
            vertx.eventBus().publish("periodic.updates", 
                "Update at: " + System.currentTimeMillis());
        });
        
        // Cancelable periodic task
        long timerId = vertx.setPeriodic(2000, id -> {
            System.out.println("This will run every 2 seconds");
            
            // Example condition to cancel the timer
            if (someCondition()) {
                vertx.cancelTimer(id);
            }
        });
        
        // One-shot timer
        vertx.setTimer(3000, id -> {
            System.out.println("This will fire once after 3 seconds");
        });
    }
    
    private boolean someCondition() {
        return false; // Example condition
    }
}

// 5. Main Deployer
public class MainDeployer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        
        // Deploy all verticles
        vertx.deployVerticle(new HttpServerExample())
            .compose(id -> vertx.deployVerticle(new WebClientExample()))
            .compose(id -> vertx.deployVerticle(new EventBusExample()))
            .compose(id -> vertx.deployVerticle(new PeriodicExample()))
            .onSuccess(id -> {
                System.out.println("All verticles deployed successfully");
            })
            .onFailure(err -> {
                System.out.println("Deployment failed: " + err.getMessage());
            });
    }
}
