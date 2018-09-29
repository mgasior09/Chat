package pl.sdacademy.javaktw7.chat.client;

import java.io.IOException;

public class ClientTerminalMain {
    public static void main(String[] args) {
        try {
            Runnable clientTerminalTask = new ClientTerminal();
            Thread clientThread = new Thread(clientTerminalTask);
            clientThread.start();
            clientThread.join();
        } catch (IOException e) {
            System.out.println("Could not connect to remote server");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Application was killed :(");
        }
    }
}
