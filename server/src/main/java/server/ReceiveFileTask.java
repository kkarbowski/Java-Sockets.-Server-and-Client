package server;

import java.io.*;
import java.net.Socket;

public class ReceiveFileTask {
    static public Void start(Socket client) throws Exception {
        Socket socket = client;
        System.out.println("Pobieranie");
        File file = null;
        String name;
        byte[] buffer = new byte[4096]; //bufor 4KB
        int readSize;

            try (DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {
                name = input.readUTF();
                try {
                    file = new File("Files", name);
                    file.createNewFile();
                } catch (Exception e) {
                    socket.close();
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                try(BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
                    while ((readSize = input.read(buffer)) != -1) {
                        output.write(buffer, 0, readSize);
                    }
                }
            }
        System.out.println("Zakończono");
        socket.close();
        return null;
    }
}