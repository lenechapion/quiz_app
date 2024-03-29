package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://localhost:3306/quiz";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
       //接続失敗はnull
    	Connection con = null; 
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            return con; 
        } catch (ClassNotFoundException e) {
            System.out.println("ドライバーの読み込みに失敗");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("データベース接続に失敗");
            e.printStackTrace();
        }
        return con; 
    }
}
