# vertx-dummy
**Create a new project via IntellijIdea via gradle and choose groovy, java and web as dependency.**

**Add below written dependency to inject vertx core**
1. compile "io.vertx:vertx-core:3.5.0"
2. compile "io.vertx:vertx-lang-groovy:3.5.0"

*Please refer **0e4a9b47bd1e04c373dc6db2ec91f025fa57fd84** commit_id*

**To Create a Vertx Instance use**

Vertx vertx = Vertx.vertx()

**To Create Verticle**
1. Create a class FirstVerticle and **extends** AbstractVerticle
2. And Override *start()* and *stop()*

**Start vertx Instance to deploy the Verticle**

Please run the the main() method of HelloWorld.java

*Please refer **ea12fdad807359b70abe2acf217ce33f75d6baf5** commit_id*

# Let use Create some inhanced Vertx application with Web Server
