package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    private static final int PORT = 1337;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Start");
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("Połączenie");
                executor.submit(() -> ReceiveFileTask.start(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Koniec");
    }
}
