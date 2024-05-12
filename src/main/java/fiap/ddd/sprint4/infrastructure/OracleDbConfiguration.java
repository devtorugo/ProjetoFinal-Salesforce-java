package fiap.ddd.sprint4.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDbConfiguration {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM553121";
    private static final String PASSWORD = "110205";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}