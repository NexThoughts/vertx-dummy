package router.com.nexthoughts

import io.vertx.core.Vertx
import router.com.nexthoughts.verticle.RouterServerSample

class RouterSample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
//        vertx.deployVerticle(new RouterVerticle())
        vertx.deployVerticle(new RouterServerSample())
    }
}
