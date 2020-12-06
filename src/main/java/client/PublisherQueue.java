package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PublisherQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherQueue.class.getName());

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            try (Socket socket = new Socket("127.0.0.1", 8080);
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
                output.writeUTF("POST /queue {\"queue\":\"weather\",\"text\":\"temperature +"
                        + i + " C\"}");
                output.flush();
                String receivedMsg = input.readUTF();
                LOGGER.info(receivedMsg);

            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}

class PublisherTopic {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherTopic.class.getName());

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            try (Socket socket = new Socket("127.0.0.1", 8080);
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
                output.writeUTF("POST /topic {\"queue\":\"weather\",\"text\":\"temperature +"
                        + i + " C\"}");
                output.flush();
                String receivedMsg = input.readUTF();
                LOGGER.info(receivedMsg);

            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
