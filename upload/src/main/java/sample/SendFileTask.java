package sample;

import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class SendFileTask extends Task<Void> {
    File file; //plik do wysłania

    private static final String STATUS_INITIALIZING = "Inicjalizacja";
    private static final String STATUS_SENDING = "Wysyłanie...";
    private static final String STATUS_DONE = "Zakończono";

    private static final int PORT = 1337;

    public SendFileTask(File file) {
        this.file = file;
    }

    @Override
    protected Void call() throws Exception {
        byte[] buffer = new byte[4096]; //bufor 4KB
        int readSize;
        int totalSize = 0;
        updateMessage(STATUS_INITIALIZING);

            try (Socket socket = new Socket("127.0.0.1", PORT);
                 DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                 BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
                output.writeUTF(file.getName());
                updateMessage(STATUS_SENDING);
                while ((readSize = input.read(buffer)) != -1) {
                    totalSize += readSize;
                    updateProgress(totalSize, file.length());
                    output.write(buffer, 0, readSize);
                }
                updateMessage(STATUS_DONE);

            }
        return null;
    }
}