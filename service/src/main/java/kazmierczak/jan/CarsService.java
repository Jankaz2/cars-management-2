package kazmierczak.jan;

import kazmierczak.jan.converter.CarsListJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.*;
import kazmierczak.jan.validator.generic.Validator;
import org.eclipse.collections.impl.collector.Collectors2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CarsService {
    @Value("${cars.filename}")
    private String filename;
    private List<Car> cars;

    @PostConstruct
    public void init() {
        cars = new CarsListJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new CarsServiceException(""))
                .stream()
                .peek(car -> Validator.validate(new CarValidator(), car))
                .collect(Collectors.toList());
    }

    /**
     * @param item criteria of sorting
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
            case COMPONENTS -> cars.stream().sorted(CarUtils.compareByComponentsSize).collect(Collectors.toList());
            case ENGINEPOWER -> cars.stream().sorted(CarUtils.compareByEnginePower).collect(Collectors.toList());
            default -> cars.stream().sorted(CarUtils.compareByWheelSize).collect(Collectors.toList());
        };

        if (descending) {
            Collections.reverse(sortedCars);
        }
        return sortedCars;
    }

    /**
     * @param bodyType car's body type
     * @param fromPrice minimin value of price range
     * @param toPrice maximum value of price range
     * @return list of cars which body type is equal to bodyType param and price is
     * in range <fromPrice; toPrice>
     */
    public List<Car> withBodyFromArgumentAndInRange(CarBodyType bodyType, BigDecimal fromPrice, BigDecimal toPrice) {
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
                .collect(Collectors.toList());
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
                .sorted()
                .collect(Collectors.toList());
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

        var priceStats = cars.stream().collect(Collectors2.summarizingBigDecimal(CarUtils.toStatsPrice));
        var mileageStats = cars.stream().collect(Collectors.summarizingInt(CarUtils.toMileage));
        var enginePowerStats = cars.stream().collect(Collectors2.summarizingBigDecimal(CarUtils.toStatsEnginePower));

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
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.collectingAndThen(
                                Collectors.mapping(CarUtils.toMileage::applyAsInt, Collectors.toList()),
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
                .collect(Collectors.groupingBy(
                        CarUtils.toWheelType
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
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
                .sorted(Comparator.comparing(CarUtils.toModel))
                .collect(Collectors.toList());
    }
}