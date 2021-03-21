package kazmierczak.jan;

import kazmierczak.jan.converter.CarsListJsonConverter;
import kazmierczak.jan.exception.CarsServiceException;
import kazmierczak.jan.validator.generic.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
}
