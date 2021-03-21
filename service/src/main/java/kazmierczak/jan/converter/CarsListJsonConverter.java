package kazmierczak.jan.converter;

import kazmierczak.jan.Car;

import java.util.List;

public class CarsListJsonConverter extends JsonConverter<List<Car>> {
    public CarsListJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
