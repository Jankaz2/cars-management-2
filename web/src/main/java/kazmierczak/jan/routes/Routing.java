package kazmierczak.jan.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.filter.CorsFilter;
import kazmierczak.jan.transformer.JsonTransformer;
import kazmierczak.jan.types.CarBodyType;
import kazmierczak.jan.types.EngineType;
import kazmierczak.jan.types.SortItem;
import kazmierczak.jan.types.StatsItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.path;

@RequiredArgsConstructor
public class Routing {
    private CarsService carsService;

    public void initRoutes() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppSpringConfig.class);
        context.refresh();
        carsService = context.getBean("carsService", CarsService.class);
        var corsFilter = new CorsFilter();
        corsFilter.apply();

        path("/cars", () -> {
            get("",
                    (request, response) -> {
                        response.header("Content-Type", "application/json;charset=utf-8");
                        return carsService.getAllCars();
                    }, new JsonTransformer()
            );

            path("/sort", () -> {
                get("/by-parameter/:item/:order",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var sortItem = request.params(":item");
                            var order = request.params(":order");
                            return carsService.sortByParameter(SortItem.valueOf(sortItem), Boolean.parseBoolean(order));
                        }, new JsonTransformer()
                );
                get("/by-engine/:type",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var type = request.params(":type");
                            return carsService.sortByEngineType(EngineType.valueOf(type));
                        }, new JsonTransformer()
                );
            });

            path("/grouped", () -> {
                get("tyre-type",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.carsGroupedByTyreType();
                        }, new JsonTransformer()
                );
            });

            path("/price", () -> {
                get("/body-type/:type/:fromPrice/:toPrice",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var bodyType = request.params(":type");
                            var fromPrice = request.params(":fromPrice");
                            var toPrice = request.params(":toPrice");
                            return carsService.withBodyAndPriceInRange(CarBodyType.valueOf(bodyType), new BigDecimal(fromPrice),
                                    new BigDecimal(toPrice));
                        }, new JsonTransformer()
                );
            });

            path("/statistics", () -> {
                get("/:statsItem",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            var statsItem = request.params(":statsItem");
                            return carsService.getStats(StatsItem.valueOf(statsItem));
                        }, new JsonTransformer()
                );

                get("/cilometers-passed",
                        (request, response) -> {
                            response.header("Content-Type", "application/json;charset=utf-8");
                            return carsService.cilometersPassedByCar();
                        }, new JsonTransformer()
                );
            });

            path("/selected", () -> {
               get("/got-components/:components",
                       (request, response) -> {
                           response.header("Content-Type", "application/json;charset=utf-8");
                           var components = request.queryParams();
                           return carsService.carsThatGotComponents(new ArrayList<>(components));
                       }, new JsonTransformer());
            });
        });
    }
}
