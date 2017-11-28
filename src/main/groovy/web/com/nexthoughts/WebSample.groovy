package web.com.nexthoughts

import io.vertx.core.Vertx
import web.com.nexthoughts.verticle.CrudVerticle

class WebSample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new CrudVerticle())
    }
}
