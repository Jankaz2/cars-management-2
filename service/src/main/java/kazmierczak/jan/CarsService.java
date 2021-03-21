package kazmierczak.jan;

import kazmierczak.jan.converter.CarsListJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.types.SortItem;
import kazmierczak.jan.validator.generic.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
}
