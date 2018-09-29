package pl.sdacademy.javaktw7.chat.server;

import pl.sdacademy.javaktw7.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerSocketReaderRunnable implements Runnable {
    private Socket clientConnection;
    private ChatLog chatLog;

    public ServerSocketReaderRunnable(Socket clientConnection, ChatLog chatLog) {
        this.clientConnection = clientConnection;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        boolean isRegistered = chatLog.register(clientConnection);
        if (isRegistered) {
            try (ObjectInputStream inputStream = new ObjectInputStream(clientConnection.getInputStream())) {
                while (isRunning) {
                    ChatMessage message = (ChatMessage) inputStream.readObject();
                    if (message == null) {
                        System.out.println("### Received null message");
                    } else if ("exit".equalsIgnoreCase(message.getMessage())) {
                        isRunning = false;
                    } else {
                        chatLog.acceptMessage(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("### Someone tried to send a message, but it failed" + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("### Received message in unknown format: " + e.getMessage());
            }
            chatLog.unregister(clientConnection);
        }
    }
}

