package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBooks {
    public static void viewBooks() {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            System.out.println("\n Available Books:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | ₹" +
                        rs.getDouble("price") + " | Copies: " +
                        rs.getInt("available_copies")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}