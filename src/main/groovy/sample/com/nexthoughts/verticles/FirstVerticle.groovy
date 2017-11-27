package sample.com.nexthoughts.verticles

import io.vertx.core.AbstractVerticle

class FirstVerticle extends AbstractVerticle {
    void start() {
        println("Verticle has been deployed")
    }

    void stop() {
        println("Verticle has been undeployed")
    }
}
