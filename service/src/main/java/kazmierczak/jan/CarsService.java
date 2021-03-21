package kazmierczak.jan;

import kazmierczak.jan.converter.CarsListJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.CarBodyType;
import kazmierczak.jan.types.EngineType;
import kazmierczak.jan.types.SortItem;
import kazmierczak.jan.validator.generic.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
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
}
