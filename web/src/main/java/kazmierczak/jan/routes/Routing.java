package kazmierczak.jan.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.filter.CorsFilter;
import kazmierczak.jan.transformer.JsonTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
    }
}
