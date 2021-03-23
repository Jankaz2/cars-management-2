package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CarsWithComponentsTest {
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
    @DisplayName("cars that got components")
    public void test1() {
        var expectedResult = List.of("PEUGEOT");
        var components = List.of("ABS", "WIFI");

        var result = carsService.carsThatGotComponents(components)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).isEqualTo(expectedResult);
    }
}
