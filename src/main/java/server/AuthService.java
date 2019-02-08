package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;

@SuppressWarnings("CheckStyle")
public class AuthService {
    /**
     * Connection
     */
    private static Connection connection;
    /**
     * Statement
     */
    private static Statement stmt;
    /**
     * Connect to DB
     *
     * @throws SQLException
     */
    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:lesson3.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param login user's login
     * @param pass  user's password
     * @return
     */
    public static String getNickByLoginAndPass(
            final String login, final String pass) {
        String sql = String.format("SELECT nickname FROM clients "
                + "WHERE login = '%s' "
                + "AND password = '%s'", login, pass);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Disconnect from DB
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
