import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservationSystem {
    private static Map<String, User> users = new HashMap<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        // Dummy user creation
        users.put("user1", new User("user1", "password1"));

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (isValidUser(username, password)) {
            System.out.println("Login successful!");

            while (true) {
                System.out.println("\n1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        System.out.println("Exiting program. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid username or password. Exiting...");
        }
    }
    private static void createDummyUsers(){
        users.put("user1", new User("user1","password1"));
        users.put("user2", new User("user2","password2"));
        users.put("user3", new User("user3","password3"));
    }
    

    private static boolean isValidUser(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    private static void makeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Train Number: ");
        String trainNumber = scanner.nextLine();

        System.out.print("Enter Train Name: ");
        String trainName = scanner.nextLine();

        System.out.print("Enter Class Type: ");
        String classType = scanner.nextLine();

        System.out.print("Enter Date of Journey: ");
        String dateOfJourney = scanner.nextLine();

        System.out.print("Enter Source Station: ");
        String from = scanner.nextLine();

        System.out.print("Enter Destination Station: ");
        String to = scanner.nextLine();

        Reservation reservation = new Reservation(trainNumber, trainName, classType, dateOfJourney, from, to);
        reservations.add(reservation);

        System.out.println("\nReservation Successful!");
        System.out.println("PNR Number: " + reservation.getPnrNumber());
    }

    private static void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter PNR Number to Cancel Reservation: ");
        String pnrNumber = scanner.nextLine();

        Reservation reservation = findReservationByPnr(pnrNumber);

        if (reservation != null) {
            reservations.remove(reservation);
            System.out.println("Reservation with PNR " + pnrNumber + " has been canceled.");
        } else {
            System.out.println("No reservation found with PNR " + pnrNumber);
        }
    }

    private static Reservation findReservationByPnr(String pnrNumber) {
        for (Reservation reservation : reservations) {
            if (reservation.getPnrNumber().equals(pnrNumber)) {
                return reservation;
            }
        }
        return null;
    }
}class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation {
    private static int nextPnrNumber = 1;

    private String pnrNumber;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String from;
    private String to;

    public Reservation(String trainNumber, String trainName, String classType, String dateOfJourney, String from, String to) {
        this.pnrNumber = generatePnrNumber();
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.from = from;
        this.to = to;
    }

    private String generatePnrNumber() {
        return "PNR" + nextPnrNumber++;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    // Getters for other fields
}


