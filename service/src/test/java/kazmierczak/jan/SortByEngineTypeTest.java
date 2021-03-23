package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.EngineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SortByEngineTypeTest {
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
    @DisplayName("when sort item instance is null")
    public void test1() {
        assertThatThrownBy(() -> carsService.sortByEngineType(null))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Enginetype object is null");
    }

    @ParameterizedTest
    @EnumSource(EngineType.class)
    @DisplayName("when sort item is not null")
    public void test2(EngineType item) {
        var expectedResult = Map.ofEntries(
                Map.entry(EngineType.LPG, List.of("FIAT")),
                Map.entry(EngineType.DIESEL, List.of("PEUGEOT", "AUDI")),
                Map.entry(EngineType.GASOLINE, List.of())
        );

        var result = carsService.sortByEngineType(item)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).isEqualTo(expectedResult.get(item));
    }
}
