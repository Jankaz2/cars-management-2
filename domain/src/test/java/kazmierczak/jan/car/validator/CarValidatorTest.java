package kazmierczak.jan.car.validator;

import kazmierczak.jan.car.*;
import kazmierczak.jan.car.validator.generic.Validator;
import kazmierczak.jan.exception.ValidatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class CarValidatorTest {
    @Test
    @DisplayName("when car object is null")
    public void test1() {
        var carValidator = new CarValidator();
        assertThatThrownBy(() -> Validator.validate(carValidator, null))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("car: object is null");
    }

    @Test
    @DisplayName("when validation has model errors")
    public void test2() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .model("FiAT")
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("model: should contain only upper case letters or white spaces");
    }

    @Test
    @DisplayName("when validation has mileage errors")
    public void test3() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .mileage(-3)
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("mileage: value cannot be less than 0");
    }

    @Test
    @DisplayName("when validation has price errors")
    public void test4() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .price(new BigDecimal(-1))
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("price: value cannot be less than 0");
    }

    @Test
    @DisplayName("when validation has body errors")
    public void test5() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .carBody(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("body: object is null");
    }

    @Test
    @DisplayName("when validation has model errors")
    public void test6() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .engine(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("engine: object is null");
    }

    @Test
    @DisplayName("when validation has wheel errors")
    public void test7() {
        var carValidator = new CarValidator();
        var car = Car.builder()
                .wheel(null)
                .build();

        assertThatThrownBy(() -> Validator.validate(carValidator, car))
                .isInstanceOf(ValidatorException.class)
                .hasMessageStartingWith("[VALIDATION ERRORS] -> ")
                .hasMessageContaining("wheel: object is null");
    }
}