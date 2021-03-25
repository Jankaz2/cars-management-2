package kazmierczak.jan.config.validator.generic;

import kazmierczak.jan.exception.ValidatorException;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator<T> {
    Map<String, String> validate(T item);

    static <T> T validate(Validator<T> validator, T item) {
        var errors = validator.validate(item);
        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + " " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException("Validation errors: " + message);
        }
        return item;
    }
}
