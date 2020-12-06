package subscriber;

import server.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ObjectJson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class SubscriberTopic implements Subscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberTopic.class.getName());
    private final String queue;
    private final DataOutputStream out;
    private final EventMessage eventMessage;
    private final Queue<ObjectJson> queueObjectJson;

    public SubscriberTopic(String queue, DataOutputStream out, EventMessage eventMessage) {
        this.queue = queue;
        this.out = out;
        this.eventMessage = eventMessage;
        this.queueObjectJson = new LinkedList<>();
    }

    @Override
    public void send() {
        eventMessage.notification();
        ObjectJson response = queueObjectJson.poll();
        try {
            if (response != null) {
                out.writeUTF(response.convertObjectToJson());
            } else {
                out.writeUTF("POST /topic {}");
            }
            out.flush();
            Thread.sleep(1000);
        } catch (InterruptedException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void getQueueObjectJson(ObjectJson objectJson) {
        queueObjectJson.offer(objectJson);
    }

    public String getQueue() {
        return queue;
    }
}
