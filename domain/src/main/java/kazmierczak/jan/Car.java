package kazmierczak.jan;

import kazmierczak.jan.types.CarBodyType;
import kazmierczak.jan.types.EngineType;
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

    /**
     *
     * @param fromPrice - minimum price in range we are looking for cars
     * @param toPrice - maximum price in range we are looking for cars
     * @return true if checking Car's price is in range <fromPrice; toPrice>
     */
    public boolean hasPriceInRange(BigDecimal fromPrice, BigDecimal toPrice) {
        return price.compareTo(fromPrice) >= 0 && price.compareTo(toPrice) <= 0;
    }

    /**
     *
     * @param type - car's body type
     * @return true if checking Car's body type is equal to body type from arguments
     */
    public boolean equalsCarBodyType(CarBodyType type) {
        return carBody.type.equals(type);
    }

    /**
     *
     * @param engineType - car's engine type
     * @return true if checking Car's engine type is equal to engine type from arguments
     */
    public boolean equalsEngineType(EngineType engineType) {
        return engine.type.equals(engineType);
    }
}
