package kazmierczak.jan;

import kazmierczak.jan.config.validator.generic.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator implements Validator<Car> {
    /**
     *
     * @param car car object we want to validate
     * @return map of errors if they exist
     */
    @Override
    public Map<String, String> validate(Car car) {
        var errors = new HashMap<String, String>();
        if (car == null) {
            errors.put("car", "object is null");
        }

        var model = car.model;
        if(hasIncorrectModel(model)){
            errors.put("model", "should contain only upper case letters or white spaces");
        }

        var mileage = car.mileage;
        if(hasIncorrectMileage(mileage)){
            errors.put("mileage", "mileage cannot be less than 0");
        }

        var price = car.price;
        if(hasIncorrectPrice(price)){
            errors.put("price", "price cannot be less than 0");
        }

        var body = car.carBody;
        if(hasIncorrectBodyType(body)){
            errors.put("body", "object is null");
        }

        var engine = car.engine;
        if(hasIncorrectEngine(engine)){
            errors.put("engine", "object is null");
        }

        var wheel = car.wheel;
        if(hasIncorrectWheel(wheel)){
            errors.put("wheel", "object is null");
        }
        return errors;
    }

    /**
     * @param model car's model
     * @return true if model is null or model name syntax is incorrect, otherwise return false
     */
    private boolean hasIncorrectModel(String model) {
        return model == null || !model.matches("[A-Z\\s]+");
    }

    /**
     * @param price car's price
     * @return true if price is null or price is less than 0, otherwise return false
     */
    private boolean hasIncorrectPrice(BigDecimal price) {
        return price == null || price.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * @param mileage car's mileage
     * @return true if mileage is less than 0, otherwise return false
     */
    private boolean hasIncorrectMileage(int mileage) {
        return mileage < 0;
    }

    /**
     * @param engine car's engine type
     * @return true if engine is null, otherwise return false
     */
    private boolean hasIncorrectEngine(Engine engine) {
        return engine == null;
    }

    /**
     * @param carBody car's car body type
     * @return true if car body is null, otherwise return false
     */
    private boolean hasIncorrectBodyType(CarBody carBody) {
        return carBody == null;
    }

    /**
     * @param wheel car's wheel
     * @return true if wheel is null, otherwise return false
     */
    private boolean hasIncorrectWheel(Wheel wheel) {
        return wheel == null;
    }
}