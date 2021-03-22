package kazmierczak.jan;

import kazmierczak.jan.types.TyreType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface CarUtils {
    /**
     * Method which compare cars by components list size
     */
    Comparator<Car> compareByComponentsSize = Comparator.comparing(car -> car.carBody.components.size());

    /**
     * Method which compare cars by engine power
     */
    Comparator<Car> compareByEnginePower = Comparator.comparing(car -> car.engine.power);

    /**
     * Method which compare cars by wheel size
     */
    Comparator<Car> compareByWheelSize = Comparator.comparing(car -> car.wheel.size);

    /**
     * Method which map Car obejct to mileage of Car object
     */
    ToIntFunction<Car> toMileage = car -> car.mileage;

    /**
     * Method which map Car object to wheel type of Car object
     */
    Function<Car, TyreType> toWheelType = car -> car.wheel.type;

    /**
     * Method which map Car obejct to price of Car object
     */
    org.eclipse.collections.api.block.function.Function<Car, BigDecimal> toStatsPrice = car -> car.price;

    /**
     * Method which map Car obejct to engine power of Car object
     */
    org.eclipse.collections.api.block.function.Function<Car, BigDecimal> toStatsEnginePower = car -> car.engine.power;
}
