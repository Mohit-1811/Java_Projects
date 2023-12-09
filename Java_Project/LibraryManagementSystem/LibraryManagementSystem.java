import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Library Management System!");

        initializeLibrary();

        while (true) {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int userTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (userTypeChoice) {
                case 1:
                    adminModule();
                    break;
                case 2:
                    userModule();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeLibrary() {
        Book book1 = new Book("B001", "The Catcher in the Rye", "J.D. Salinger");
        Book book2 = new Book("B002", "To Kill a Mockingbird", "Harper Lee");

        library.addBook(book1);
        library.addBook(book2);

        Member member1 = new Member("M001", "John Doe", "john@example.com");
        Member member2 = new Member("M002", "Jane Doe", "jane@example.com");

        library.addMember(member1);
        library.addMember(member2);
    }

    private static void adminModule() {
        while (true) {
            System.out.println("\nAdmin Module:");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. View Reports");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (adminChoice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    addMember();
                    break;
                case 4:
                    viewReports();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBook() {
        System.out.print("\nEnter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        Book newBook = new Book(bookId, title, author);
        library.addBook(newBook);

        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.print("\nEnter Book ID to remove: ");
        String bookId = scanner.nextLine();

        Book bookToRemove = library.getBookById(bookId);
        if (bookToRemove != null) {
            library.removeBook(bookId);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void addMember() {
        System.out.print("\nEnter Member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter Member Name: ");
        String memberName = scanner.nextLine();
        System.out.print("Enter Member Email: ");
        String memberEmail = scanner.nextLine();

        Member newMember = new Member(memberId, memberName, memberEmail);
        library.addMember(newMember);

        System.out.println("Member added successfully.");
    }

    private static void viewReports() {
        System.out.println("\nLibrary Reports:");
        System.out.println("Number of Books: " + library.getBooks().size());
        System.out.println("Number of Members: " + library.getMembers().size());
    }

    private static void userModule() {
        while (true) {
            System.out.println("\nUser Module:");
            System.out.println("1. View Books");
            System.out.println("2. Search Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Pay Fine");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    payFine();
                    break;
                case 6:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewBooks() {
        System.out.println("\nAvailable Books:");
        for (Book book : library.getBooks().values()) {
            System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Status: " +
                    (book.isAvailable() ? "Available" : "Not Available"));
        }
    }

    private static void searchBook() {
        System.out.print("\nEnter Book ID to search: ");
        String bookId = scanner.nextLine();

        Book book = library.getBookById(bookId);
        if (book != null) {
            System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Status: " +
                    (book.isAvailable() ? "Available" : "Not Available"));
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void issueBook() {
        System.out.print("\nEnter Member ID: ");
        String memberId = scanner.nextLine();

        Member member = library.getMemberById(memberId);
        if (member != null) {
            System.out.print("Enter Book ID to issue: ");
            String bookId = scanner.nextLine();

            Book book = library.getBookById(bookId);
            if (book != null && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Book issued successfully.");
            } else {
                System.out.println("Book not available or not found.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void returnBook() {
        System.out.print("\nEnter Member ID: ");
        String memberId = scanner.nextLine();

        Member member = library.getMemberById(memberId);
        if (member != null) {
            System.out.print("Enter Book ID to return: ");
            String bookId = scanner.nextLine();

            Book book = library.getBookById(bookId);
            if (book != null && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book not issued or not found.");
            }
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void payFine() {
        System.out.print("\nEnter Member ID: ");
        String memberId = scanner.nextLine();

        Member member = library.getMemberById(memberId);
        if (member != null) {
            System.out.print("Enter the amount to pay: ");
            int amount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            int remainingFine = member.getFine() - amount;
            member.setFine(Math.max(remainingFine, 0));
            System.out.println("Fine payment successful. Remaining fine: " + member.getFine());
        } else {
            System.out.println("Member not found.");
        }
    }
}

class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Member {
    private String memberId;
    private String name;
    private String email;
    private int fine;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.fine = 0;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }
}

class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;

    public Library() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getBookId(), book);
    }

    public void removeBook(String bookId) {
        books.remove(bookId);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Book getBookById(String bookId) {
        return books.get(bookId);
    }

    public Member getMemberById(String memberId) {
        return members.get(memberId);
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public Map<String, Member> getMembers() {
        return members;
    }
}