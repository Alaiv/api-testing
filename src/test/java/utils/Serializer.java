package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class Serializer {
    private static final ObjectMapper om = new ObjectMapper();

    @SneakyThrows
    public static <T> String extractFrom(T item) {
        return om.writeValueAsString(item);
    }

    @SneakyThrows
    public static <T> T extractTo(String item, Class<T> clazz) {
        return om.convertValue(item, clazz);
    }
}
