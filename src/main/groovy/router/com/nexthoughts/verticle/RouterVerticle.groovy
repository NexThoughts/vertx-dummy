package router.com.nexthoughts.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.ext.web.Router

class RouterVerticle extends AbstractVerticle {

    static Router router

    void start() {
        createRouter(vertx)
        createServer(vertx)
    }

    void stop() {

    }

    static void createRouter(Vertx vertx) {
        router = Router.router(vertx)

        router.route("/intro").handler({ routingContext ->
            def response = routingContext.response()
            response.setChunked(true)
            response.write("route1\n")
            routingContext.vertx().setTimer(5000, { tid ->
                routingContext.next()
            })
        })

        router.route("/intro").handler({ routingContext ->
            def response = routingContext.response()
            response.write("route2\n")

            // Call the next matching route after a 5 second delay
            routingContext.vertx().setTimer(5000, { tid ->
                routingContext.next()
            })
        })

        router.route("/intro").handler({ routingContext ->

            def response = routingContext.response()
            response.write("route3")

            // Now end the response
            response.putHeader("content-type", "text/plain")
            response.end("Hello World from Vert.x-Web!")
        })
    }

    static void createServer(Vertx vertx) {
        HttpServer server = vertx.createHttpServer()
        server.requestHandler(router.&accept).listen(8085)
    }
}
