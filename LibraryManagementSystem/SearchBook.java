package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SearchBook {
    public static void searchBook(Scanner sc) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            System.out.print("Enter book title to search: ");
            String title = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title ILIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | ₹" +
                        rs.getDouble("price") + " | Copies: " +
                        rs.getInt("available_copies")
                );
                found = true;
            }

            if (!found) {
                System.out.println("❌ No books found with that title!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}