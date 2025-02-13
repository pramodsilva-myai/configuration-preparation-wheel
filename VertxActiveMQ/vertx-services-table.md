| Library | Service Type | Simple Code Example |
|---------|-------------|---------------------|
| **vertx-core** |
| | HTTP Server | `vertx.createHttpServer().requestHandler(req -> req.response().end("Hello")).listen(8080);` |
| | Event Bus | `vertx.eventBus().consumer("channel", msg -> System.out.println(msg.body()));` |
| | File System | `vertx.fileSystem().readFile("file.txt", result -> System.out.println(result.result()));` |
| | Timers | `vertx.setPeriodic(1000, id -> System.out.println("Timer fired"));` |
| **vertx-web** |
| | Router | `Router.router(vertx).get("/api").handler(ctx -> ctx.json(new JsonObject()));` |
| | Static Handler | `router.route().handler(StaticHandler.create("webroot"));` |
| | Body Handler | `router.route().handler(BodyHandler.create());` |
| | Session Handler | `router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));` |
| **vertx-web-client** |
| | Web Client | `WebClient.create(vertx).get(8080, "localhost", "/").send(ar -> {});` |
| | Form Submit | `client.post("/form").sendForm(MultiMap.caseInsensitiveMultiMap().set("key", "value"));` |
| | File Upload | `client.post("/upload").sendMultipartForm(MultipartForm.create().textFileUpload());` |
| **vertx-auth** |
| | Basic Auth | `AuthenticationHandler.create(BasicAuthProvider.create(vertx, authProvider));` |
| | JWT Auth | `JWTAuth.create(vertx, new JWTAuthOptions());` |
| | OAuth2 | `OAuth2Auth.create(vertx, new OAuth2Options());` |
| **vertx-redis-client** |
| | Redis Client | `Redis.createClient(vertx).connect().onSuccess(conn -> conn.get("key"));` |
| | Redis Pub/Sub | `redis.subscriber().onSuccess(sub -> sub.subscribe("channel"));` |
| **vertx-pg-client** |
| | PostgreSQL Query | `PgPool.client(vertx, connectOptions).query("SELECT * FROM users").execute();` |
| | Prepared Statement | `client.preparedQuery("SELECT * FROM users WHERE id=$1").execute(Tuple.of(id));` |
| **vertx-mqtt** |
| | MQTT Server | `MqttServer.create(vertx).endpointHandler(endpoint -> {}).listen();` |
| | MQTT Client | `MqttClient.create(vertx).connect(1883, "localhost", done -> {});` |
| **vertx-amqp-client** |
| | AMQP Receiver | `AmqpClient.create(vertx).connect(ar -> ar.result().createReceiver("queue"));` |
| | AMQP Sender | `AmqpClient.create(vertx).connect(ar -> ar.result().createSender("queue"));` |
| **vertx-mail-client** |
| | Mail Client | `MailClient.create(vertx).sendMail(new MailMessage().setFrom("from@ex.com"));` |
| **vertx-config** |
| | Config Retrieval | `ConfigRetriever.create(vertx).getConfig(ar -> System.out.println(ar.result()));` |
| | Store Config | `ConfigStoreOptions options = new ConfigStoreOptions().setType("file");` |
| **vertx-health-check** |
| | Health Check | `HealthChecks.create(vertx).register("db-check", promise -> promise.complete());` |
| **vertx-circuit-breaker** |
| | Circuit Breaker | `CircuitBreaker.create("name", vertx).execute(promise -> service.call());` |
| **vertx-reactive-streams** |
| | Publisher | `ReactiveReadStream<Buffer> publisher = ReactiveReadStream.readStream();` |
| | Subscriber | `ReactiveWriteStream<Buffer> subscriber = ReactiveWriteStream.writeStream();` |
