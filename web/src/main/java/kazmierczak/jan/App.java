package kazmierczak.jan;

import kazmierczak.jan.routes.Routing;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(8080);
        var routes = new Routing();
        routes.initRoutes();
    }
}
