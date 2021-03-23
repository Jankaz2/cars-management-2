package kazmierczak.jan;

import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.CarBodyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WithBodyTypeAndPriceInRangeTest {
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
    @DisplayName("when body type instance is null")
    public void test1() {
        assertThatThrownBy(() -> carsService.withBodyAndPriceInRange(null, new BigDecimal(1), new BigDecimal(2)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("BodyType is null");
    }

    @Test
    @DisplayName("when fromPrice instance is null")
    public void test2() {
        assertThatThrownBy(() -> carsService.withBodyAndPriceInRange(CarBodyType.COMBI, null, new BigDecimal(2)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("FromPrice is null");
    }

    @Test
    @DisplayName("when toPrice instance is null")
    public void test3() {
        assertThatThrownBy(() -> carsService.withBodyAndPriceInRange(CarBodyType.SEDAN, new BigDecimal(1), null))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("ToPrice is null");
    }

    @Test
    @DisplayName("when fromPrice is greater than toPrice")
    public void test4() {
        assertThatThrownBy(() -> carsService.withBodyAndPriceInRange(CarBodyType.COMBI, new BigDecimal(2), new BigDecimal(1)))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("Wrong range, minimum cannot be greater than maximum");
    }

    @ParameterizedTest
    @EnumSource(CarBodyType.class)
    @DisplayName("when all arguments are not null")
    public void test5(CarBodyType bodyType) {
        var expectedResult = Map.ofEntries(
                Map.entry(CarBodyType.SEDAN, List.of("FIAT")),
                Map.entry(CarBodyType.HATCHBACK, List.of("AUDI")),
                Map.entry(CarBodyType.COMBI, List.of("PEUGEOT"))
        );

        var result = carsService.withBodyAndPriceInRange(bodyType, new BigDecimal(115), new BigDecimal(130))
                .stream()
                .map(CarUtils.toModel)
                .collect(Collectors.toList());

        assertThat(result).isEqualTo(expectedResult.get(bodyType));
    }
}
