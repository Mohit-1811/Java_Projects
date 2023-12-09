import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMSystem {
    private static List<User> users = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        initializeUsers();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        loginUser(userId, pin);

        if (currentUser != null) {
            System.out.println("Login successful!");

            while (true) {
                displayMenu();

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        displayTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal(scanner);
                        break;
                    case 3:
                        performDeposit(scanner);
                        break;
                    case 4:
                        performTransfer(scanner);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }

        scanner.close();
    }

    private static void initializeUsers() {
        users.add(new User("user1", "1234", 1000.0));
        users.add(new User("user2", "5678", 2000.0));
        users.add(new User("user3", "0000", 3000.0));
        // Add more users as needed
    }

    private static void loginUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin().equals(pin)) {
                currentUser = user;
                return;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. View Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void displayTransactionHistory() {
        List<Transaction> transactions = currentUser.getTransactionHistory();
        System.out.println("\nTransaction History:");

        for (Transaction transaction : transactions) {
            System.out.println("Type: " + transaction.getType() + ", Amount: " + transaction.getAmount());
        }
    }

    private static void performWithdrawal(Scanner scanner) {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getAccount().getBalance()) {
            currentUser.getAccount().withdraw(amount);
            currentUser.addTransaction(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal successful. New balance: " + currentUser.getAccount().getBalance());
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    private static void performDeposit(Scanner scanner) {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            currentUser.getAccount().deposit(amount);
            currentUser.addTransaction(new Transaction("Deposit", amount));
            System.out.println("Deposit successful. New balance: " + currentUser.getAccount().getBalance());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private static void performTransfer(Scanner scanner) {
        System.out.print("Enter recipient's User ID: ");
        String recipientId = scanner.nextLine();

        User recipient = findUserById(recipientId);

        if (recipient != null && !recipient.equals(currentUser)) {
            System.out.print("Enter transfer amount: ");
            double amount = scanner.nextDouble();

            if (amount > 0 && amount <= currentUser.getAccount().getBalance()) {
                currentUser.getAccount().transfer(recipient.getAccount(), amount);
                currentUser.addTransaction(new Transaction("Transfer to " + recipientId, amount));
                recipient.addTransaction(new Transaction("Transfer from " + currentUser.getUserId(), amount));

                System.out.println("Transfer successful. New balance: " + currentUser.getAccount().getBalance());
            } else {
                System.out.println("Invalid transfer amount or insufficient funds.");
            }
        } else {
            System.out.println("Recipient not found or cannot transfer to yourself.");
        }
    }

    private static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void transfer(Account recipient, double amount) {
        withdraw(amount);
        recipient.deposit(amount);
    }
}

class User {
    private String userId;
    private String pin;
    private List<Transaction> transactionHistory;
    private Account account;

    public User(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        this.account = new Account(initialBalance);
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public Account getAccount() {
        return account;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }
}