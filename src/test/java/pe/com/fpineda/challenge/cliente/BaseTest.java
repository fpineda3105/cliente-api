package pe.com.fpineda.challenge.cliente;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author fpineda
 */
public class BaseTest {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }
}
