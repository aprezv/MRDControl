package MRDControl;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Armando
 */
public class ConnectionManager {

    private static Connection con;
    private static String url = "jdbc:mysql://localhost/mrdcontrol";
    private static String username = "root";
    private static String password = "Alcohol145.";

    private static void initializeConnection() throws SQLException {
        con = DriverManager.getConnection(url, username, password);

    }

    public static Connection getConnection() throws SQLException{
        if (con == null) {
            initializeConnection();
        }

        return con;
    }
}
