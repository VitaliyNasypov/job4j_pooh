package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Subscriber {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            threadPool.execute(new SubscriberThread("topic"));
        }
    }
}

class SubscriberThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class.getName());
    private String mode;

    public SubscriberThread(String mode) {
        this.mode = mode;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", 8080);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());) {
            output.writeUTF("GET /" + mode + "/weather");
            String receivedMsg = "";
            while (!receivedMsg.equals("POST /" + mode + " {}")) {
                receivedMsg = input.readUTF();
                LOGGER.info(receivedMsg);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
