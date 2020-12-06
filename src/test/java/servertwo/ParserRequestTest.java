package servertwo;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import server.ParserRequest;

public class ParserRequestTest {
    @Test
    public void shouldReturnNoEmptyArray() {
        String postRequest = "POST /queue {\"queue\":\"weather\",\"text\":\"temperature +18 C\"}";
        String getRequest = "GET /topic/weather";
        ParserRequest parserRequest = new ParserRequest();
        String[] postArray = parserRequest.parsRequest(postRequest);
        String[] getArray = parserRequest.parsRequest(getRequest);
        Assertions.assertArrayEquals(
                new String[]{"POST", "queue", "{\"queue\":\"weather\","
                        + "\"text\":\"temperature +18 C\"}"},
                postArray);
        Assertions.assertArrayEquals(
                new String[]{"GET", "topic", "weather"},
                getArray);
    }
}
