package kazmierczak.jan.service;

import kazmierczak.jan.CarsService;
import kazmierczak.jan.config.AppSpringConfig;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.CarStatistics;
import kazmierczak.jan.types.Statistics;
import kazmierczak.jan.types.StatsItem;
import org.eclipse.collections.impl.collector.Collectors2;
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

public class StatsServiceTest {
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
    @DisplayName("when stats item instance is null")
    public void test1() {
        assertThatThrownBy(() -> carsService.getStats(null))
                .isInstanceOf(CarsServiceException.class)
                .hasMessage("StatsItem argument is null");
    }

    @ParameterizedTest
    @EnumSource(StatsItem.class)
    @DisplayName("when stats item is not null")
    public void test2(StatsItem statsItem) {
        var prices = List.of(
                new BigDecimal(120), new BigDecimal(130), new BigDecimal(115)
        );
        var mileages = List.of(
                15000, 80000, 2500
        );
        var enginePowers = List.of(
                new BigDecimal(200), new BigDecimal(180), new BigDecimal(210)
        );

        var expectedPriceStats = prices.stream().collect(Collectors2.summarizingBigDecimal(x -> x));
        var expectedMileageStats = mileages.stream().collect(Collectors.summarizingInt(x -> x));
        var expectedEnginePowersStats = enginePowers.stream().collect(Collectors2.summarizingBigDecimal(x -> x));

        var expectedCarPriceStatistics = CarStatistics.builder()
                .priceStatistics(Statistics.fromBigDecimalSummaryStatistics(expectedPriceStats)).build();
        var expectedCarMileageStatistics = CarStatistics.builder()
                .mileageStatistics(Statistics.fromIntSummaryStatistics(expectedMileageStats)).build();
        var expectedCarEnginePowersStatistics = CarStatistics.builder()
                .enginePowerStatistics(Statistics.fromBigDecimalSummaryStatistics(expectedEnginePowersStats)).build();

        var expectedResult = Map.ofEntries(
                Map.entry(StatsItem.PRICE, expectedCarPriceStatistics),
                Map.entry(StatsItem.MILEAGE, expectedCarMileageStatistics),
                Map.entry(StatsItem.ENGINEPOWER, expectedCarEnginePowersStatistics)
        );

        var result = carsService.getStats(statsItem);

        assertThat(result).isEqualTo(expectedResult.get(statsItem));
    }
}
