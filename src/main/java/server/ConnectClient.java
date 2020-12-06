package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscriber.Subscriber;
import subscriber.SubscriberQueue;
import subscriber.SubscriberTopic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Класс обрабатывает Socket клиента в отдельном потоке.
 */

public class ConnectClient implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectClient.class.getName());
    private final Socket client;
    private final EventMessage eventMessage;
    private Subscriber subscriber;

    public ConnectClient(Socket client, EventMessage eventMessage) {
        this.client = client;
        this.eventMessage = eventMessage;
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
             DataInputStream in = new DataInputStream(client.getInputStream())) {
            ParserRequest parserRequest = new ParserRequest();
            String[] requestArray = parserRequest.parsRequest(in.readUTF());
            if (requestArray[0].equals("POST")) {
                eventMessage.addMessage(requestArray);
                out.writeUTF("POST /OK");
            } else if (requestArray[0].equals("GET")) {
                subscriber = requestArray[1].equals("queue")
                        ? new SubscriberQueue(requestArray[2], out, eventMessage)
                        : new SubscriberTopic(requestArray[2], out, eventMessage);
                eventMessage.subscribe(subscriber);
                while (!client.isClosed()) {
                    subscriber.send();
                }
            } else {
                out.writeUTF("Request BAD");
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (subscriber != null) {
                eventMessage.unsubscribe(subscriber);
            }
        }
    }
}
