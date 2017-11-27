package router.com.nexthoughts.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler

class RouterServerSample extends AbstractVerticle {
    Router router
    Map productList = [:]

    void start() {
        setUpInitialData()
        createRouter(vertx)
        createServer(vertx)
    }

    void stop() {

    }

    def createRouter(Vertx vertx) {
        router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.get("/products/:productID").handler(this.&handleGetProduct)
        router.put("/products/:productID").handler(this.&handleAddProduct)
        router.get("/products").handler(this.&handleListProducts)
    }

    def createServer(Vertx vertx) {
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void handleGetProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID")
        HttpServerResponse response = routingContext.response()
        if (productID == null) {
            sendError(400, response)
        } else {
            JsonObject product = productList.get(productID) as JsonObject
            if (product == null) {
                sendError(404, response)
            } else {
                response.putHeader("content-type", "application/json").end(product.encodePrettily())
            }
        }
    }

    void handleAddProduct(RoutingContext routingContext) {
        String productID = routingContext.request().getParam("productID")
        HttpServerResponse response = routingContext.response()
        if (productID == null) {
            sendError(400, response)
        } else {
            JsonObject product = routingContext.getBodyAsJson()
            if (product == null) {
                sendError(400, response)
            } else {
                productList.put(productID, product)
                response.end()
            }
        }
    }

    void handleListProducts(RoutingContext routingContext) {
        JsonArray arr = new JsonArray()
        productList.each { k, v ->
            arr.add(v)
        }
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily())
    }

    void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end()
    }

    void setUpInitialData() {
        addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", "3.99").put("weight", "150"))
        addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", "5.99").put("weight", "100"))
        addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", "1.00").put("weight", "80"))
    }

    void addProduct(JsonObject product) {
        productList.put(product.getString("id"), product);
    }
}
