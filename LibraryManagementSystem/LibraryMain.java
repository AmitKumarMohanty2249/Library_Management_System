package LibraryManagementSystem.LibraryManagementSystem;

import java.util.Scanner;

public class LibraryMain {
    private static final Scanner sc = new Scanner(System.in); 

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========= Library Management System =========");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {   
                System.out.println("❌ Invalid input. Please enter a number.");
                sc.nextLine(); 
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1: AddBook.addBook(sc); break;
                case 2: ViewBooks.viewBooks(); break;
                case 3: SearchBook.searchBook(sc); break;
                case 4: IssueBook.issueBook(sc); break;
                case 5: ReturnBook.returnBook(sc); break;
                case 6: DeleteBook.deleteBook(sc); break;
                case 7: System.out.println(" Exiting..."); return;
                default: System.out.println("❌ Invalid choice!");
            }
        }
    }
}