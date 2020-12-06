package servertwo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.EventMessage;
import server.ObjectJson;
import server.ParserRequest;

public class EventMessageTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventMessageTest.class.getName());

    @Test
    public void shouldInteractProperlyPublisherAndSubscriber() {
        EventMessage eventMessage = new EventMessage();
        ObjectJson objectJsonRequest =
                new ObjectJson("{\"queue\":\"weather\",\"text\":\"temperature +18 C\"}");
        Thread publisher = new Thread(
                () -> {
                    ParserRequest parserRequest =
                            new ParserRequest();
                    String request =
                            "POST /queue {\"queue\":\"weather\",\"text\":\"temperature +18 C\"}";
                    String[] requestArray = parserRequest.parsRequest(request);
                    eventMessage.addMessage(requestArray);
                }
        );
        publisher.start();
        try {
            publisher.join();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        ObjectJson responseOne = eventMessage.getMessage("weather");
        ObjectJson responseTwo = eventMessage.getMessage("weather");
        Assertions.assertEquals(objectJsonRequest, responseOne);
        Assertions.assertNull(responseTwo);
    }
}
