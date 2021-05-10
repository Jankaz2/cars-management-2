package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CilometersPassedByCarTest {
    private CarsService carsService;

    @BeforeEach
    public void setup() {
        var context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("test");
        context.register(AppSpringConfig.class);
        context.refresh();
        carsService = context.getBean("carsService", CarsService.class);
    }

    @Test
    @DisplayName("comparison of cars with cilometers passed by them")
    public void test1() {
       /* var expectedResult = Map.ofEntries(
                Map.entry("AUDI", 15000),
                Map.entry("FIAT", 80000),
                Map.entry("PEUGEOT", 2500)
        );

        var result = carsService
                .cilometersPassedByCar()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().model,
                        Map.Entry::getValue
                ));

        assertThat(result).isEqualTo(expectedResult);*/
    }
}
