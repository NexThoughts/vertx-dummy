package web.com.nexthoughts.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer

class WebVerticle extends AbstractVerticle {

    void start() {
        setupServer(vertx)
    }

    void stop() {

    }

    static void setupServer(Vertx vertx) {
        HttpServer server = vertx.createHttpServer()
        server.requestHandler({ request ->
            def response = request.response()
            response.putHeader("content-type", "text/plain")
            response.end("Hello World!!!")
        })

        server.listen(8085)
    }
}
