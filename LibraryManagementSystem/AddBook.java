package LibraryManagementSystem.LibraryManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class AddBook {
    public static void addBook(Scanner sc) {
        try (Connection conn = DBConnection.connect()) {

           
            String title;
            while (true) {
                System.out.print("Enter Title: ");
                title = sc.nextLine();
                if (title.matches("[a-zA-Z .]+")) {   
                    break;
                } else {
                    System.out.println("❌ Invalid Title! Only alphabets are allowed.");
                }
            }

            
            String author;
            while (true) {
                System.out.print("Enter Author: ");
                author = sc.nextLine();
                if (author.matches("[a-zA-Z .]+")) {  
                    break;
                } else {
                    System.out.println("❌ Invalid Author! Only alphabets are allowed.");
                }
            }

          
            double price = 0;
            while (true) {
                System.out.print("Enter Price: ");
                String input = sc.nextLine();
                if (input.matches("\\d+(\\.\\d{1,2})?")) {  
                    price = Double.parseDouble(input);
                    break;
                } else {
                    System.out.println("❌ Invalid Price! Enter numbers only.");
                }
            }

           
            int copies = 0;
            while (true) {
                System.out.print("Enter Available Copies: ");
                String input = sc.nextLine();
                if (input.matches("\\d+")) {  
                    copies = Integer.parseInt(input);
                    break;
                } else {
                    System.out.println("❌ Invalid Copies! Enter a whole number only.");
                }
            }

            
            String sql = "INSERT INTO books (title, author, price, available_copies) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, copies);

            pstmt.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
