package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.car.CarUtils;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.types.TyreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupedByTyreTypeTest {
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
    @DisplayName("comparison of tyre type and list of cars that got this type of tyre")
    public void test1() {
        var expectedResult = Map.ofEntries(
                Map.entry(TyreType.SUMMER, List.of("AUDI", "PEUGEOT")),
                Map.entry(TyreType.WINTER, List.of("FIAT"))
        );

        var result = carsService.carsGroupedByTyreType()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        value -> value.getValue()
                                .stream()
                                .map(CarUtils.toModel)
                                .collect(Collectors.toList())
                ));

        assertThat(result).isEqualTo(expectedResult);
    }
}
