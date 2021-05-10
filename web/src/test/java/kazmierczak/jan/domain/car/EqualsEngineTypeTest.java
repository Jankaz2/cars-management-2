package kazmierczak.jan.domain.car;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.Engine;
import kazmierczak.jan.types.EngineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsEngineTypeTest {
    @Test
    @DisplayName("when equalsEngineType method works correctly")
    public void test1() {
        var car = Car.builder()
                .engine(Engine.builder()
                        .type(EngineType.DIESEL)
                        .build())
                .build();

        var engineType = EngineType.DIESEL;
        var result = car.equalsEngineType(engineType);

        assertThat(result).isTrue();
    }
}
