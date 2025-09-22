package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ReturnBook {
    public static void returnBook(Scanner sc) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            System.out.print("Enter Book ID to return: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Book returned successfully!");
            } else {
                System.out.println("❌ Invalid Book ID!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
