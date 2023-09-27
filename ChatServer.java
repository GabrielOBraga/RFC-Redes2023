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

    private class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
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
