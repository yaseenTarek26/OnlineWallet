import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection geConnection() throws ClassNotFoundException, SQLException {
      Class.forName("oracle.jdbc.OracleDriver");
      Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
       return conn;
        }

}
