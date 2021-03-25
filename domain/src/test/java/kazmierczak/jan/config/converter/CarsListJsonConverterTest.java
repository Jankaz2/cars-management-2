package kazmierczak.jan.config.converter;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.CarBody;
import kazmierczak.jan.car.Engine;
import kazmierczak.jan.car.Wheel;
import kazmierczak.jan.extension.CarsListJsonConverterExtension;
import kazmierczak.jan.types.CarBodyColor;
import kazmierczak.jan.types.CarBodyType;
import kazmierczak.jan.types.EngineType;
import kazmierczak.jan.types.TyreType;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CarsListJsonConverterExtension.class)
@RequiredArgsConstructor
public class CarsListJsonConverterTest {
    private final CarsListJsonConverter carsListJsonConverter;

    @Test
    @DisplayName("when json conversion works correctly")
    public void test1() {
        var expectedResult = List.of(
                Car.builder()
                        .model("AUDI")
                        .price(new BigDecimal(120))
                        .mileage(15000)
                        .engine(Engine.builder()
                                .type(EngineType.DIESEL)
                                .power(new BigDecimal(200))
                                .build())
                        .carBody(CarBody.builder()
                                .color(CarBodyColor.BLACK)
                                .type(CarBodyType.HATCHBACK)
                                .components(List.of(
                                        "DARKGLASSES",
                                        "WIFI"
                                )).build())
                        .wheel(Wheel.builder()
                                .model("MICHELIN")
                                .size(12)
                                .type(TyreType.SUMMER)
                                .build())
                        .build(),
                Car.builder()
                        .model("FIAT")
                        .price(new BigDecimal(130))
                        .mileage(80000)
                        .engine(Engine.builder()
                                .type(EngineType.LPG)
                                .power(new BigDecimal(180))
                                .build())
                        .carBody(CarBody.builder()
                                .color(CarBodyColor.RED)
                                .type(CarBodyType.SEDAN)
                                .components(List.of(
                                        "GPS",
                                        "WARM WHEEL"
                                )).build())
                        .wheel(Wheel.builder()
                                .model("MICHELIN")
                                .size(17)
                                .type(TyreType.WINTER)
                                .build())
                        .build()
        );

        var resultFromFile = carsListJsonConverter.fromJson().orElseThrow();

        assertDoesNotThrow(() -> assertThat(resultFromFile)
                .hasSize(2)
                .containsExactlyElementsOf(expectedResult));
    }
}
