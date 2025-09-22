package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DeleteBook {
    public static void deleteBook(Scanner sc) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            System.out.print("Enter Book ID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Book deleted successfully!");
            } else {
                System.out.println("❌ Invalid Book ID!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}