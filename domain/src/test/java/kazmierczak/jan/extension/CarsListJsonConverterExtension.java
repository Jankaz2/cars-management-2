package kazmierczak.jan.extension;

import kazmierczak.jan.config.converter.CarsListJsonConverter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CarsListJsonConverterExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarsListJsonConverter.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var filename = "C:\\Users\\Hp\\OneDrive\\kmprograms\\Java\\projects\\cars-managament-2\\domain\\src\\main\\resources\\files\\test\\cars-test.json";
        return new CarsListJsonConverter(filename);
    }
}
