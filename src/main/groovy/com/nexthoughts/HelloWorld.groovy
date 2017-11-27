package com.nexthoughts

import com.nexthoughts.verticles.FirstVerticle
import io.vertx.core.Vertx

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
