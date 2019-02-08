package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class Server {

    public static final int PORT = 9191;
    public static final String IP_ADDRESS = "127.0.0.1";

    private Vector<ClientHandler> clients;

    /**
     * Constructor
     * @throws SQLException throw SQL xception
     */
    public Server() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            AuthService.connect();
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен. Ожидаем клиентов...");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
                // clients.add(new ClientHandler(this, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    /**
     * Add new client
     * @param client client's handler
     */
    public void subscribe(final ClientHandler client) {
        clients.add(client);
    }

    /**
     * Remove client
     * @param client client's handler
     */
    public void unsubscribe(final ClientHandler client) {
        clients.remove(client);
    }

    /**
     * Broadcast message
     * @param msg MEssage to send
     */
    public void broadcastMsg(final String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
