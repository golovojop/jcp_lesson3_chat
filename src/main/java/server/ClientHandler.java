package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private DataOutputStream out;
    private DataInputStream in;

    private Server server;
    private Socket socket;
    private String nick;

    /**
     * @param server Server class
     * @param socket Client's socket
     */
    public ClientHandler(final Server server, final Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                            if (newNick != null) {
                                sendMsg("/authok");
                                nick = newNick;
                                server.subscribe(this);
                                break;
                            } else {
                                sendMsg("Неверный логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.equals("/end")) {
                            out.writeUTF("/serverclosed");
                            break;
                        }
                        server.broadcastMsg(nick + ": " + str);
                        System.out.println("Client: " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param msg Message to send
     */
    public void sendMsg(final String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
