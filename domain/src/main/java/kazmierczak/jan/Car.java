package kazmierczak.jan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class Car {
    String model;
    BigDecimal price;
    int mileage;
    Engine engine;
    CarBody carBody;
    Wheel wheel;
}
