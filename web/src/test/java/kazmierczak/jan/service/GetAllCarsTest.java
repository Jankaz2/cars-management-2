package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllCarsTest {
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
    @DisplayName("when getAllCars method works correctly")
    public void test1() {
        var expectedResult = List.of(
                "AUDI", "FIAT", "PEUGEOT"
        );
        var result = carsService.getAllCars()
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result)
                .hasSize(3)
                .containsExactlyElementsOf(expectedResult);
    }
}
