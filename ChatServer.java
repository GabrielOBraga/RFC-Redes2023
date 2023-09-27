import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Copyright (c) 2023
 * Created by Gabriel Oliveira Braga
 * Updated by 27/09/2023
 * Project: https://github.com/GabrielOBraga/RFC-Redes2023
 * Description: Projeto de Curso Bacharelado de Ciências da Computação, Instituto Federal de Goiás - Campus Anápolis
 * Discipline: Redes de Computadores - 02/2023
 */
public class ChatServer {

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private Map<String, ClientHandler> loggedInUsers;

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start(5000);
    }

    public void start(int port) {
        clients = new ArrayList<>();
        loggedInUsers = new HashMap<>();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor BragaZap iniciado na porta: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo Passarinho: " + clientSocket);

                ClientHandler client = new ClientHandler(clientSocket);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    private synchronized void sendCommandsList(ClientHandler client){
        client.sendMessage("\n\n______________________________________________________________\n");
        client.sendMessage("Comandos para se comunicar o BragaZap!\n");
        client.sendMessage("/logout - caso você queira sair do Zap!");
        client.sendMessage("/users - para você ver quais dos seus colegas estão online!");
        client.sendMessage("/changeuser - para você ser um ninja e alterar seu nome de usuário!");
        client.sendMessage("/help - cuidado, recursividade encontrada! Error: /help\n");
    }

    private synchronized void sendUserList(ClientHandler client) {
        StringBuilder userList = new StringBuilder();
        userList.append("Passarinhos online:");
        for (ClientHandler loggedInUser : loggedInUsers.values()) {
            userList.append("\n- ").append(loggedInUser.getUsername());
        }
        client.sendMessage(userList.toString());
    }

    private synchronized void loginUser(String username, ClientHandler client) {
        loggedInUsers.put(username, client);
        client.setUsername(username);
        broadcast(username + " acabou de pousar no chat.", null);
        sendUserList(client);
    }

    private synchronized void logoutUser(ClientHandler client) {
        client.sendMessage("Você deslogou com sucesso, até a proxima!");
        loggedInUsers.remove(client.getUsername());
        clients.remove(client);
        broadcast(client.getUsername() + " caiu do ninho (deslogou).", null);
        sendUserList(null);
    }

    private synchronized void changeUser(String newUsername, ClientHandler client) {
        loggedInUsers.remove(client.getUsername());
        loggedInUsers.put(newUsername, client);
        client.setUsername(newUsername);
        broadcast(client.getUsername() + " é um ninja, acabou de trocar seu nome para " + newUsername, null);
        sendUserList(client);
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void sendMessage(String message) {
            writer.println(message);
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                writer.println("Bem Vindo passarinho, ao BragaZap!");

                writer.println("Como deseja ser chamado? ");
                String username = reader.readLine();
                loginUser(username, this);

                String clientMessage;
                while ((clientMessage = reader.readLine()) != null) {
                    if (clientMessage.startsWith("/logout")) {
                        logoutUser(this);
                        break;
                    } else if (clientMessage.startsWith("/users")) {
                        sendUserList(this);
                    } else if (clientMessage.startsWith("/help")) {
                        sendCommandsList(this);
                    }else if (clientMessage.startsWith("/changeuser ")) {
                        String newUsername = clientMessage.substring(12);
                        changeUser(newUsername, this);
                    } else {
                        broadcast(username + ": " + clientMessage, this);
                    }
                }

                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}