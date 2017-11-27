package template.com.nexthoughts

import io.vertx.core.Vertx
import template.com.nexthoughts.verticle.TemplateVerticle

class TemplateSample {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new TemplateVerticle())
    }
}