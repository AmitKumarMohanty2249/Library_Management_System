package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/LibraryDB"; 
    
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "123";

    public static Connection connect() {
        try {
            
            Class.forName("org.postgresql.Driver");  
           

            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Connection failed! Check DB URL, username, password.");
            e.printStackTrace();
        }
        return null; 
    }
}
