package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.SortItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class SortServiceTest {
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
        assertThatThrownBy(() -> carsService.sortByParameter(null, false))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Sort item is not correct");
    }

    @ParameterizedTest
    @EnumSource(SortItem.class)
    @DisplayName("when sort item instance is not null and sorting is ascending")
    public void test2(SortItem item) {
        var expectedResult = Map.ofEntries(
                Map.entry(SortItem.WHEELSIZE, List.of("AUDI", "FIAT", "PEUGEOT")),
                Map.entry(SortItem.ENGINEPOWER, List.of("FIAT", "AUDI", "PEUGEOT")),
                Map.entry(SortItem.COMPONENTS, List.of("AUDI", "FIAT", "PEUGEOT"))
        );

        var result = carsService.sortByParameter(item, false)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyElementsOf(expectedResult.get(item));
    }

    @ParameterizedTest
    @EnumSource(SortItem.class)
    @DisplayName("when sort item instance is not null and sorting is descending")
    public void test3(SortItem item) {
        var expectedResult = Map.ofEntries(
                Map.entry(SortItem.WHEELSIZE, List.of("PEUGEOT", "FIAT", "AUDI")),
                Map.entry(SortItem.ENGINEPOWER, List.of("PEUGEOT", "AUDI", "FIAT")),
                Map.entry(SortItem.COMPONENTS, List.of("PEUGEOT", "FIAT", "AUDI"))
        );

        var result = carsService.sortByParameter(item, true)
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).containsExactlyElementsOf(expectedResult.get(item));
    }
}
