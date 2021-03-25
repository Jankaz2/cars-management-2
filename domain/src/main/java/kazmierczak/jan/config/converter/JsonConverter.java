package kazmierczak.jan.config.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kazmierczak.jan.exception.ValidatorException;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {
    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    /**
     *
     * @param item - item we want to convert to json
     */
    public void toJson(T item) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)) {
            fileWriter.write(gson.toJson(item));
        } catch (Exception e) {
            throw new JsonConverterException(e.getMessage());
        }
    }

    /**
     *
     * @return item we get in json converted to type we want
     */
    public Optional<T> fromJson() {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            System.err.println("From json conversion exception");
        }
        return Optional.empty();
    }
}
