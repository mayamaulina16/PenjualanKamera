package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    public static Connection getConnection(){
        Connection c = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting..");
            c = DriverManager.getConnection(
                    "jdbc:mysql://localhost/penjualan_kamera",
                    "root",
                    ""
            );
            System.out.println("Connected!");
        } catch (ClassNotFoundException e) {
            System.out.println("Connection error!");
        } catch (SQLException e) {
            System.out.println("SQL error!");
        }
        return c;
    }
}
