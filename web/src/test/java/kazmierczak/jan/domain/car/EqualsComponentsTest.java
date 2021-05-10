package kazmierczak.jan.domain.car;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.CarBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualsComponentsTest {
    @Test
    @DisplayName("when equalsComponents method works correctly")
    public void test1() {
        var car = Car.builder()
                .carBody(CarBody.builder()
                        .components(List.of(
                                "WIFI",
                                "AIR CONDITIONING"
                        ))
                        .build())
                .build();
        var components = List.of("WIFI", "AIR CONDITIONING");
        var result = car.equalsComponents(components);

        assertThat(result).isTrue();
    }
}
