package sample.com.nexthoughts

import io.vertx.core.Vertx
import sample.com.nexthoughts.verticles.FirstVerticle

class HelloWorld {
    public static void main(String[] args) {
        println "Creating the Vertx Instance"
        Vertx vertx = Vertx.vertx()
        println "Vertx Instance has been created $vertx"

        println "Going to deploy the verticle"
        FirstVerticle firstVerticle = new FirstVerticle()
        vertx.deployVerticle(firstVerticle)
    }
}
