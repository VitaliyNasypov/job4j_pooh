package subscriber;

import server.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ObjectJson;

import java.io.DataOutputStream;
import java.io.IOException;

public class SubscriberQueue implements Subscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberQueue.class.getName());
    private final String queue;
    private final DataOutputStream out;
    private final EventMessage eventMessage;

    public SubscriberQueue(String queue, DataOutputStream out, EventMessage eventMessage) {
        this.queue = queue;
        this.out = out;
        this.eventMessage = eventMessage;
    }

    @Override
    public void send() {
        ObjectJson response = eventMessage.getMessage(queue);
        try {
            if (response != null) {
                out.writeUTF("POST /queue " + response.convertObjectToJson());
            } else {
                out.writeUTF("POST /queue {}");
            }
            out.flush();
            Thread.sleep(1000);
        } catch (InterruptedException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public String getQueue() {
        return queue;
    }
}
