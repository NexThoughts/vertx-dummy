package template.com.nexthoughts.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine

class TemplateVerticle extends AbstractVerticle {

    void start() throws Exception {
        Router router = Router.router(vertx)
        FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create()

        router.get().handler({ ctx ->
            ctx.put("name", "Hi Vertex!")

            templateEngine.render(ctx, "templates/index.ftl", { res ->
                if (res.succeeded()) {
                    ctx.response().end(res.result())
                } else {
                    ctx.fail(res.cause())
                }
            })
        })
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void stop() {

    }
}
