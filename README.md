# vertx-dummy
**Create a new project via IntellijIdea via gradle and choose groovy, java and web as dependency.**

**Add below written dependency to inject vertx core**
1. compile "io.vertx:vertx-core:3.5.0"
2. compile "io.vertx:vertx-lang-groovy:3.5.0"

****

*Please refer **0e4a9b47bd1e04c373dc6db2ec91f025fa57fd84** commit_id*

**To Create a Vertx Instance use**

Vertx vertx = Vertx.vertx()

**To Create Verticle**
1. Create a class FirstVerticle and **extends** AbstractVerticle
2. And Override *start()* and *stop()*

**Start vertx Instance to deploy the Verticle**

****

Please run the the main() method of **HelloWorld.java**

*Please refer **ea12fdad807359b70abe2acf217ce33f75d6baf5** commit_id*

# Let use Create some inhanced Vertx application with Web Server

**Add Vertx HttpServer dependency**
1. compile 'io.vertx:vertx-web:3.5.0'

**Create a Verticle who will Start a HttpServer**

HttpServer server = vertx.createHttpServer()

**Add a Receiver to handle the request**

server.requestHandler({ request -> })

**Create a Response from above created handler**

request.response().putHeader("content-type", "text/plain").end("Hello World!!!")

**Add Listener Port to HttpServer**

server.listen(8085)
****
Please run the main() method of **web.com.nexthoughts.WebSample.java**

*Please refer **5e1b3be65d0e4666167da571ad7badebc6d29e00** commit_id*

****

**More Complex HttpServer with routing**

Please follow the router.com.nexthoughts.RouterSample

*Please refer **62af23f6861b3c3408feb8db10f24d56d4c95d7f** commit_id*

# Let add templates to create a complete Web application
Vertx has support of many templates like *Thymeleaf*, *Apache FreeMarker* etc.

** Add Vertx template dependency**
1. compile 'io.vertx:vertx-web-templ-thymeleaf:3.5.0'
2. compile 'io.vertx:vertx-web-templ-freemarker:3.5.0'

*Please refer **template.com.nexthoughts.TemplateSample** for the templating example*

*Please refer the **0045f17f078b5633350cd383930e4acbcfdbf0f5** commit_id*

#CRUD Operation
**I have created the basic CRUD operation**
*Please refer the **07650ecca5b19f567092927bf63ff672ff145c69** commit_id*
