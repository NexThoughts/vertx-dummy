package web.com.nexthoughts.verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.sql.SQLConnection
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine

class CrudVerticle extends AbstractVerticle {
    JDBCClient client = null
    FreeMarkerTemplateEngine engine = null

    void start() {
        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/demo_lending?autoreconnect=true")
                .put("user", "root")
                .put("password", "nextdefault")
                .put("driver_class", "com.mysql.jdbc.Driver")
                .put("max_pool_size", 30)

        client = JDBCClient.createShared(vertx, config)
        engine = FreeMarkerTemplateEngine.create()
        Router router = Router.router(vertx)

        router.route().handler(BodyHandler.create())

        router.get("/").handler({ ctx ->
            ctx.put("title", "Vert.x Web")
            ctx.put("name", "User List")

            engine.render(ctx, "templates/createUser.ftl", { res ->
                if (res.succeeded()) {
                    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html").end(res.result())
                } else {
                    ctx.fail(res.cause())
                }
            })
        })

        router.route("/users*").handler({ ctx ->
            client.getConnection({ res ->
                if (res.failed()) {
                    ctx.fail(res.cause())
                } else {
                    SQLConnection conn = res.result()
                    ctx.put("conn", conn)
                    ctx.addHeadersEndHandler({ done ->
                        conn.close({ v ->

                        })
                    })
                    ctx.next()
                }
            })
        }).failureHandler({ ctx ->
            SQLConnection conn = ctx.get("conn")
            if (conn != null) {
                conn.close({ v ->
                })
            }
        })

        router.get("/users/:userID").handler(this.&handleGetUser)
        router.post("/users").handler(this.&handleAddUser)
        router.get("/users").handler(this.&handleListUsers)
        router.delete("/users/:userId").handler(this.&handleDeleteUser)
        vertx.createHttpServer().requestHandler(router.&accept).listen(8085)
    }

    void handleGetUser(RoutingContext ctx) {
        ctx.put("title", "User Info")
        String userID = ctx.request().getParam("userID")
        HttpServerResponse response = ctx.response()
        if (!userID) {
            sendError(400, response)
        } else {
            SQLConnection conn = ctx.get("conn")
            String queryStr = "SELECT id, firstName, lastName, age, email FROM user where id = ?"
            conn.queryWithParams(queryStr, new JsonArray().add(Integer.parseInt(userID)), { query ->
                if (query.failed()) {
                    println query.cause()
                    sendError(500, response)
                } else {
                    if (query.result().getNumRows() == 0) {
                        sendError(404, response)
                    } else {
                        JsonArray array = new JsonArray()
                        query.result().results.each {
                            JsonObject obj = new JsonObject()
                            obj.put("id", it[0])
                            obj.put("firstName", it[1])
                            obj.put("lastName", it[2])
                            obj.put("age", it[3])
                            obj.put("email", it[4])
                            array.add(obj)
                        }
                        String userTable = generateTable(array)
                        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html").end(userTable)
                    }
                }
            })
        }
    }

    void handleDeleteUser(RoutingContext ctx) {
        ctx.put("title", "Delete User")
        String userID = ctx.request().getParam("userID")
        HttpServerResponse response = ctx.response()
        if (!userID) {
            sendError(400, response)
        } else {
            SQLConnection conn = ctx.get("conn")
            String queryStr = "DELETE FROM user where id = ?"
            conn.queryWithParams(queryStr, new JsonArray().add(Integer.parseInt(userID)), { query ->
                if (query.failed()) {
                    println query.cause()
                    sendError(500, response)
                } else {
                    response.putHeader("content-type", "text/html").end("User with id ${userID} has been succesfully removed")
                }
            })
        }
    }

    void handleAddUser(RoutingContext ctx) {
        HttpServerResponse response = ctx.response()
        ctx.put("title", "Add User")
        println("****************************8")
        println("****************************8")
        println("****************************8")
        println("****************************8")
        SQLConnection conn = ctx.get("conn")
        conn.updateWithParams("INSERT INTO user (firstName, lastName, age, email, id) VALUES (?, ?, ?, ?, ?)",
                new JsonArray()
                        .add(ctx.request().getFormAttribute("firstName"))
                        .add(ctx.request().getFormAttribute("lastName"))
                        .add(ctx.request().getFormAttribute("age"))
                        .add(ctx.request().getFormAttribute("email"))
                        .add(ctx.request().getFormAttribute("userId")),
                { query ->
                    if (query.failed()) {
                        sendError(500, response)
                    } else {
                        response.end()
                    }
                })
    }

    void handleListUsers(RoutingContext ctx) {
        ctx.put("title", "Users List")
        HttpServerResponse response = ctx.response()
        SQLConnection conn = ctx.get("conn")
        println(conn.properties)
        conn.query("SELECT id, firstName, lastName, age, email FROM user", { query ->
            if (query.failed()) {
                println query.cause()
                sendError(500, response)
            } else {
                JsonArray arr = new JsonArray()
                query.result().results.forEach(arr.&add)
                JsonArray array = new JsonArray()
                query.result().results.each {
                    JsonObject obj = new JsonObject()
                    obj.put("id", it[0])
                    obj.put("firstName", it[1])
                    obj.put("lastName", it[2])
                    obj.put("age", it[3])
                    obj.put("email", it[4])
                    array.add(obj)
                }
                String userTable = generateTable(array)

                ctx.response().putHeader("content-type", "text/html").end(userTable)
            }
        })
    }

    void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end()
    }

    static String generateTable(JsonArray array) {
        String data = getTableHeader()
        array.each { JsonObject object ->
            println(object)
            data += """<tr>
        <td>${object.getInteger("id")}</td>
        <td>${object.getString("firstName")}</td>
        <td>${object.getString("lastName")}</td>
        <td>${object.getString("email")}</td>
        <td>${object.getInteger("age")}</td>
    </tr>"""
        }

        data += getTableFooter()
        return data
    }

    static String getTableHeader() {
        return """<table border=1>
    <thead>
    <tr>
        <td>ID</td>
        <td>First Name</td>
        <td>Last Name</td>
        <td>Email</td>
        <td>Age</td>
    </tr>
    </thead>
    <tbody>"""
    }

    static String getTableFooter() {
        """ </tbody>
</table>"""
    }
}
