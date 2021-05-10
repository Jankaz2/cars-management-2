package kazmierczak.jan;

import kazmierczak.jan.car.Car;
import kazmierczak.jan.car.CarValidator;
import kazmierczak.jan.config.converter.CarsListJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.*;
import kazmierczak.jan.car.validator.generic.Validator;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import static java.util.Collections.*;
import static java.util.Comparator.*;
import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.joining;
import static kazmierczak.jan.car.CarUtils.*;

@Service
public class CarsService {
    @Value("${cars.filename}")
    private String filename;
    private List<Car> cars;

    @PostConstruct
    public void init() {
        cars = new CarsListJsonConverter("/" + jarPath() + "/resources/" + filename)
                .fromJson()
                .orElseThrow(() -> new CarsServiceException("Cannot read data from file " + filename))
                .stream()
                .peek(car -> Validator.validate(new CarValidator(), car))
                .collect(toList());
    }

    /**
     * @return jar path
     */
    public String jarPath() {
        try {
            var path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            var pathElements = path.split("/");
            var size = pathElements.length;
            return Arrays
                    .stream(pathElements)
                    .limit(size - 1)
                    .skip(1)
                    .collect(joining("/"));
        } catch (Exception e) {
            throw new CarsServiceException(e.getMessage());
        }
    }

    /**
     * @return list of all available cars
     */
    public List<Car> getAllCars() {
        return cars;
    }

    /**
     * @param item       criteria of sorting
     * @param descending sorting order
     * @return list of cars sorted by item
     * if descending is true then returned list is sorted descending,
     * otherwise ascending
     */
    public List<Car> sortByParameter(SortItem item, boolean descending) {
        if (item == null) {
            throw new CarsServiceException("Sort item is not correct");
        }

        var sortedCars = switch (item) {
            case COMPONENTS -> cars.stream().sorted(compareByComponentsSize).collect(toList());
            case ENGINEPOWER -> cars.stream().sorted(compareByEnginePower).collect(toList());
            default -> cars.stream().sorted(compareByWheelSize).collect(toList());
        };

        if (descending) {
            reverse(sortedCars);
        }
        return sortedCars;
    }

    /**
     * @param bodyType  car's body type
     * @param fromPrice minimum value of price range
     * @param toPrice   maximum value of price range
     * @return list of cars which body type is equal to bodyType param and price is
     * in range <fromPrice; toPrice>
     */
    public List<Car> withBodyAndPriceInRange(CarBodyType bodyType, BigDecimal fromPrice, BigDecimal toPrice) {
        if (fromPrice == null) {
            throw new CarsServiceException("FromPrice is null");
        }

        if (toPrice == null) {
            throw new CarsServiceException("ToPrice is null");
        }

        if (bodyType == null) {
            throw new CarsServiceException("BodyType is null");
        }

        if (fromPrice.compareTo(toPrice) > 0) {
            throw new CarsServiceException("Wrong range, minimum cannot be greater than maximum");
        }

        return cars
                .stream()
                .filter(car -> car.equalsCarBodyType(bodyType) && car.hasPriceInRange(fromPrice, toPrice))
                .collect(toList());
    }

    /**
     * @param engineType type of car's engine
     * @return list of cars sorted by param engine type
     */
    public List<Car> sortByEngineType(EngineType engineType) {
        if (engineType == null) {
            throw new CarsServiceException("Enginetype object is null");
        }
        return cars
                .stream()
                .filter(car -> car.equalsEngineType(engineType))
                .sorted(compareByModel)
                .collect(toList());
    }

    /**
     * @param statsItem - price or mileage or engine power
     * @return stats of all cars for statsItem param:
     * the minimum value, the maximum value, the average value
     */
    public CarStatistics getStats(StatsItem statsItem) {
        if (statsItem == null) {
            throw new CarsServiceException("StatsItem argument is null");
        }

        var priceStats = cars.stream().collect(Collectors2.summarizingBigDecimal(toStatsPrice));
        var mileageStats = cars.stream().collect(summarizingInt(toMileage));
        var enginePowerStats = cars.stream().collect(Collectors2.summarizingBigDecimal(toStatsEnginePower));

        return switch (statsItem) {
            case PRICE -> CarStatistics.builder().priceStatistics(Statistics.fromBigDecimalSummaryStatistics(priceStats)).build();
            case ENGINEPOWER -> CarStatistics.builder().enginePowerStatistics(Statistics.fromBigDecimalSummaryStatistics(enginePowerStats)).build();
            default -> CarStatistics.builder().mileageStatistics(Statistics.fromIntSummaryStatistics(mileageStats)).build();
        };
    }

    /**
     * @return comparison where key is car object
     * and value is cilometers passed by this car
     */
    public Map<Car, Integer> cilometersPassedByCar() {
        return cars
                .stream()
                .collect(groupingBy(
                        identity(),
                        collectingAndThen(
                                mapping(toMileage::applyAsInt, toList()),
                                mileages -> mileages.stream().findFirst().orElseThrow()
                        )
                ));
    }

    /**
     * @return comparison where key is type of tyre
     * and value is list of cars that got this
     * tyre type
     */
    public Map<TyreType, List<Car>> carsGroupedByTyreType() {
        return cars
                .stream()
                .collect(groupingBy(
                        toWheelType
                ))
                .entrySet()
                .stream()
                .sorted(comparing(e -> e.getValue().size()))
                .collect(toMap(
                        Entry::getKey, Entry::getValue,
                        (v1, v2) -> v1, LinkedHashMap::new)
                );
    }

    /**
     * @param components - list of components we want to find in car
     * @return list of cars that got all of the components
     * get as a param
     */
    public List<Car> carsThatGotComponents(List<String> components) {
        if (components == null) {
            throw new CarsServiceException("Components list is null");
        }
        return cars
                .stream()
                .filter(car -> car.equalsComponents(components))
                .sorted(comparing(toModel))
                .collect(toList());
    }
}