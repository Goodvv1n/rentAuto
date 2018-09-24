package ru.pleshkov.rentAuto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Утилиты, помогающие написанию тестов
 * @author pleshkov on 24.09.2018.
 */
public class TestUtils {
    /**
     * Пасим объект в JSON
     * @param object объект
     * @return результат
     * @throws JsonProcessingException ошибка
     */
    public static String parseObjecctToJSON(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
