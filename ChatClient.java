import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Copyright (c) 2023
 * Created by Gabriel Oliveira Braga
 * Updated by 27/09/2023
 * Project: https://github.com/GabrielOBraga/RFC-Redes2023
 * Description: Projeto de Curso Bacharelado de Ciências da Computação, Instituto Federal de Goiás - Campus Anápolis
 * Discipline: Redes de Computadores - 02/2023
 */
public class ChatClient {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader consoleReader;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.connect("localhost", 5000);
    }

    public void connect(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String serverMessage = reader.readLine();
            System.out.println(serverMessage); // Welcome message from server

            System.out.print("Enter your username: ");
            String username = consoleReader.readLine();
            writer.println(username);

            Thread messageReceiverThread = new Thread(this::receiveMessages);
            messageReceiverThread.start();

            String clientMessage;
            while ((clientMessage = consoleReader.readLine()) != null) {
                writer.println(clientMessage);
                if (clientMessage.startsWith("/logout")) {
                    break;
                }
            }

            messageReceiverThread.join(); // Wait for the message receiver thread to finish

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessages() {
        try {
            String serverMessage;
            while ((serverMessage = reader.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}