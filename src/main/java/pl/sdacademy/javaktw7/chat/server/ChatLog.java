package pl.sdacademy.javaktw7.chat.server;

import pl.sdacademy.javaktw7.chat.model.ChatMessage;
import pl.sdacademy.javaktw7.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class ChatLog {
    private Map<Socket, ObjectOutputStream> clientConnections;

    public ChatLog() {
        clientConnections = new LinkedHashMap<>();
    }

    public boolean register(Socket newClient) {
        try {
            ObjectOutputStream newClientStream = new ObjectOutputStream(newClient.getOutputStream());
            clientConnections.put(newClient, newClientStream);
        } catch (IOException e) {
            System.out.println("### Someone tried to connect, but was rejected: " +
                    e.getMessage());
            return false;
        }
        return true;
    }

    public boolean unregister(Socket existingClient) {
        if (clientConnections.containsKey(existingClient)) {
            ObjectOutputStream connectionToBeClosed = clientConnections.remove(existingClient);
            try {
                connectionToBeClosed.close();

            } catch (IOException ignored) {
            }
            return true;
        }
        return false;
    }

    public void acceptMessage(ChatMessage chatMessage) {
        DatedChatMessage datedChatMessage = new DatedChatMessage(chatMessage);

    }
}
