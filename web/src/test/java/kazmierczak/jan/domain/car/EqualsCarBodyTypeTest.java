package kazmierczak.jan.domain.car;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.CarBody;
import kazmierczak.jan.types.CarBodyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsCarBodyTypeTest {
    @Test
    @DisplayName("when equalsCarBodyType methd works correctly")
    public void test1() {
        var car = Car.builder()
                .carBody(CarBody.builder()
                        .type(CarBodyType.HATCHBACK)
                        .build())
                .build();
        var bodyType = CarBodyType.HATCHBACK;
        var result = car.equalsCarBodyType(bodyType);

        assertThat(result).isTrue();
    }
}
