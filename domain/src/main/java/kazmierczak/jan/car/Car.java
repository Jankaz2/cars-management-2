package kazmierczak.jan.car;

import kazmierczak.jan.types.CarBodyType;
import kazmierczak.jan.types.EngineType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class Car {
    String model;
    BigDecimal price;
    int mileage;
    Engine engine;
    CarBody carBody;
    Wheel wheel;

    /**
     * @param fromPrice - minimum price in range we are looking for cars
     * @param toPrice   - maximum price in range we are looking for cars
     * @return true if checking Car's price is in range <fromPrice; toPrice>
     */
    public boolean hasPriceInRange(BigDecimal fromPrice, BigDecimal toPrice) {
        return price.compareTo(fromPrice) >= 0 && price.compareTo(toPrice) <= 0;
    }

    /**
     * @param type - car's body type
     * @return true if checking Car's body type is equal to body type from arguments
     */
    public boolean equalsCarBodyType(CarBodyType type) {
        return carBody.type.equals(type);
    }

    /**
     * @param engineType - car's engine type
     * @return true if checking Car's engine type is equal to engine type from arguments
     */
    public boolean equalsEngineType(EngineType engineType) {
        return engine.type.equals(engineType);
    }

    /**
     * @param comps - list of components we want to check whether car got them
     * @return true if checking Car's components are equal to components list from arguments
     */
    public boolean equalsComponents(List<String> comps) {
        return carBody.components.containsAll(comps);
    }
}
