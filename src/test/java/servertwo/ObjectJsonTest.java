package servertwo;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import server.ObjectJson;

public class ObjectJsonTest {
    @Test
    public void shouldCorrectConvertJsonToObject() {
        String jsonText = "{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}";
        ObjectJson objectJson = new ObjectJson(jsonText);
        Assertions.assertEquals("weather", objectJson.getQueue());
        Assertions.assertEquals("temperature +18 C", objectJson.getText());
    }

    @Test
    public void shouldCorrectConvertObjectToJson() {
        String jsonText = "{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}";
        ObjectJson objectJson = new ObjectJson(jsonText);
        Assertions.assertEquals(jsonText, objectJson.convertObjectToJson());
    }
}
