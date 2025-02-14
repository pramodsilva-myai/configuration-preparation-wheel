package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);
    private static final String EVENT_ADDRESS = "message.queue";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle())
                .onSuccess(id -> logger.info("MainVerticle deployed successfully"))
                .onFailure(err -> logger.error("Failed to deploy MainVerticle", err));

        // Deploy the worker verticle
//        vertx.deployVerticle(new WorkerVerticle())
//                .onSuccess(id -> logger.info("WorkerVerticle deployed successfully"))
//                .onFailure(err -> logger.error("Failed to deploy WorkerVerticle", err));
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // Handle POST requests to /message
        router.post("/message").handler(ctx -> {
            String message = ctx.getBodyAsJson().getString("role");

            // Publish message to event bus
            vertx.eventBus().publish(EVENT_ADDRESS, message);

            ctx.response()
                    .putHeader("content-type", "application/json")
                    .end("{\"status\":\"Message sent: " + message + "\"}");
        });

        // Handle GET requests to /status
        router.get("/status").handler(ctx ->
                ctx.response()
                        .putHeader("content-type", "application/json")
                        .end("{\"status\":\"Server is running\"}"));

        // Start the HTTP server
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080)
                .onSuccess(server -> {
                    logger.info("HTTP server started on port 8080");
                    startPromise.complete();
                })
                .onFailure(startPromise::fail);
    }
}