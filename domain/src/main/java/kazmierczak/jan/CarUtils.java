package kazmierczak.jan;

import java.util.Comparator;

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

}
