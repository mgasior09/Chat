package pl.sdacademy.javaktw7.chat.client;

import pl.sdacademy.javaktw7.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {
    private final Socket socket;

    public ClientTerminal() throws IOException {
        socket = new Socket("adres, na który będziemy się łączyć", 5567);
    }

    public void run() {
        try (ObjectOutputStream connection = new ObjectOutputStream(socket.getOutputStream())) {
            Scanner getUserName = new Scanner(System.in);
            String userName = getUserName.nextLine();
            String message = "";
            Scanner getMessage = new Scanner(System.in);
            while (!message.equalsIgnoreCase("exit")) {
                System.out.println("> ");
                message = getMessage.nextLine();
                ChatMessage messageToSend = new ChatMessage(userName, message);
                connection.writeObject(messageToSend);
                connection.flush();
            }

        } catch (IOException e) {
            System.out.println("Could not prepare object connection");
            System.out.println(e.getMessage());
        }
        System.out.println("Disconnected");
    }
}

