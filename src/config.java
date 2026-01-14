
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class config {
 
    private static Connection koneksi;

    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/login";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.err.println("Koneksi gagal " + e.getMessage());
        }
        return koneksi;
    }
}

    

