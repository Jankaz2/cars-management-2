package kazmierczak.jan.config.converter;

import kazmierczak.jan.car.Car;

import java.util.List;

public class CarsListJsonConverter extends JsonConverter<List<Car>> {
    public CarsListJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
