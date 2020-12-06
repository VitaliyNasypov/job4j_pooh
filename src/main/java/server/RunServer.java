package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunServer.class.getName());
    private static final EventMessage EVENT_MESSAGE = new EventMessage();

    public static void main(String[] args) {
        try {
            RunServer runServer = new RunServer();
            runServer.runServer();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Метод запускает сервер. Создаётся Thread Pool.
     * После подключения нового клиента, Socket клиента передаётся в
     * Thread Pool для обработки запроса.
     * Поток Main ждёт новых клиентов.
     */
    private void runServer() throws IOException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        int port = 8080;
        ServerSocket server = new ServerSocket(port);
        while (!server.isClosed()) {
            Socket client = server.accept();
            threadPool.execute(new ConnectClient(client, EVENT_MESSAGE));
        }
        threadPool.shutdown();
    }
}