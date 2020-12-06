package servertwo;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.RunServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectClientTest.class.getName());

    @Test
    public void clientShouldConnectToServer() {
        Thread server = new Thread(
                () -> {
                    RunServer.main(new String[0]);
                }
        );
        server.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
        Thread client = new Thread(
                () -> {
                    try (Socket socket = new Socket("127.0.0.1", 8080);
                         DataInputStream input = new DataInputStream(socket.getInputStream());
                         DataOutputStream output =
                                 new DataOutputStream(socket.getOutputStream());) {
                        output.writeUTF("POST /test");
                        output.flush();
                        String receivedMsg = input.readUTF();
                        Assertions.assertEquals("Request BAD", receivedMsg);
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
        );
        client.start();
    }
}
