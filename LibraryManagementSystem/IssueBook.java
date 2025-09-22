package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class IssueBook {
    public static void issueBook(Scanner sc) {
        try (Connection conn = DBConnection.connect()) {
            if (conn == null) {
                System.out.println("❌ Database connection failed!");
                return;
            }

            System.out.print("Enter Book ID to issue: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ? AND available_copies > 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Book issued successfully!");
            } else {
                System.out.println("❌ Book not available or invalid ID!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
