/**
 * 
 */
package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author akanksha
 *
 */
public class ConnectionConfiguration {
    public static final String URL = "jdbc:mysql://localhost:3306/appdirect";
    /**
     * In my case username is "root" *
     */
    public static final String USERNAME = "root";
    /**
     * In my case password is "akanksha" *
     */
    public static final String PASSWORD = "akanksha";
 
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return connection;
    }
 
}
