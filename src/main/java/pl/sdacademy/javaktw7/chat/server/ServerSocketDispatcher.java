package pl.sdacademy.javaktw7.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDispatcher implements Runnable {
    private final ChatLog chatLog;
    private final ServerSocket serverSocket;

    public ServerSocketDispatcher() throws IOException {
        serverSocket = new ServerSocket(5567);
        chatLog = new ChatLog();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket newClient = serverSocket.accept();
                Runnable readerRunnable = new ServerSocketReaderRunnable(newClient, chatLog);
                System.out.println("### New client connected");
                new Thread(readerRunnable).start();
            } catch (IOException e) {
                System.out.println("### Could not connect new client: " + e.getMessage());
            }
        }
    }
}
