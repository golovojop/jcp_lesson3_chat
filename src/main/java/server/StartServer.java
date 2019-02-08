package server;

import java.sql.SQLException;

/**
 * Server starter
 */
@SuppressWarnings("CheckStyle")
public class StartServer {

    /**
     * Entry point
     *
     * @param args startup args
     * @throws SQLException popup exceptions from AuthService
     */
    public static void main(final String[] args) throws SQLException {
        new Server();
    }
}
