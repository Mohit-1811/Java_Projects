import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineExaminationSystem {
    private static Map<String, User> users = new HashMap<>();
    private static Map<Integer, Question> questionBank = new HashMap<>();
    private static Exam currentExam;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();
        initializeQuestions();

        System.out.println("Welcome to the Online Examination System!");

        // Login
        User currentUser = login();
        if (currentUser != null) {
            System.out.println("Login successful!");

            // Update Profile and Password
            updateProfileAndPassword(currentUser);

            // Start Exam with Countdown Timer
            startExam();

            // Timer and auto submit (Assuming a fixed time limit for simplicity)
            simulateTimerAndAutoSubmit();

            // Selecting answers for MCQs
            answerMCQs();

            // Closing session and Logout
            closeSessionAndLogout(currentUser);
        } else {
            System.out.println("Invalid username or password. Exiting...");
        }
    }

    private static void initializeUsers() {
        users.put("student1", new User("student1", "pass123", "Student"));
        // Add more users as needed
    }

    private static void initializeQuestions() {
        Map<String, String> options1 = new HashMap<>();
        options1.put("A", "Moscow");
        options1.put("B", "Paris");
        options1.put("C", "New Delhi");
        options1.put("D", "Lisbon");
        questionBank.put(1, new Question("What is the capital of Inida?", options1, "C"));

        Map<String, String> options2 = new HashMap<>();
        options2.put("A", "Java");
        options2.put("B", "Python");
        options2.put("C", "C++");
        options2.put("D", "JavaScript");
        questionBank.put(2, new Question("Which programming language is known for its 'Write Once, Run Anywhere' principle?", options2, "A"));
    }

    private static User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    private static void updateProfileAndPassword(User currentUser) {
        System.out.print("Do you want to update your profile? (yes/no): ");
        String updateProfileChoice = scanner.nextLine().toLowerCase();
        if (updateProfileChoice.equals("yes")) {
            System.out.print("Enter new profile: ");
            String newProfile = scanner.nextLine();
            currentUser.updateProfile(newProfile);
            System.out.println("Profile updated successfully!");
        }

        System.out.print("Do you want to update your password? (yes/no): ");
        String updatePasswordChoice = scanner.nextLine().toLowerCase();
        if (updatePasswordChoice.equals("yes")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.updatePassword(newPassword);
            System.out.println("Password updated successfully!");
        }
    }

    private static void startExam() {
        System.out.println("Starting the exam...");
    
        // Initialize the exam
        currentExam = new Exam(questionBank);
    
        // Simulate a countdown timer and question answering concurrently
        Thread timerThread = new Thread(() -> startCountdownTimer(300)); // 300 seconds = 5 minutes
        Thread examThread = new Thread(() -> answerMCQs());
    
        timerThread.start();
        examThread.start();
    
        try {
            timerThread.join();
            examThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        System.out.println("Exam completed!");
    }
    

    private static void startCountdownTimer(int duration) {
        System.out.println("Timer started. Remaining time: " + duration + " seconds");
    }

    private static void answerMCQs() {
        while (currentExam.getCurrentQuestion() != null) {
            Question currentQuestion = currentExam.getCurrentQuestion();
            System.out.println("\n" + currentQuestion.getQuestion());
            System.out.println("Options:");
            for (Map.Entry<String, String> option : currentQuestion.getOptions().entrySet()) {
                System.out.println(option.getKey() + ". " + option.getValue());
            }
            System.out.print("Your answer (Enter the option letter): ");
            String answer = scanner.nextLine().toUpperCase();
            currentExam.answerQuestion(answer);
        }
    
        System.out.println("All questions answered!");
    
        // Simulate auto-submit after answering all questions
        try {
            Thread.sleep(1000); // Optional delay before auto-submit (1 second in this example)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        System.out.println("Automatically submitting the exam...");
    }
    

    private static void simulateTimerAndAutoSubmit() {
        // Simulating timer (in seconds)
        int examDuration = 300; // 5 minutes

        try {
            Thread.sleep(examDuration * 1000); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Time's up! Automatically submitting the exam...");
    }

    private static void closeSessionAndLogout(User currentUser) {
        System.out.println("\nClosing the session and logging out...");
        System.out.println("Thank you for using the Online Examination System!");
    }
}

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password, String profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public void updateProfile(String newProfile) {
        this.profile = newProfile;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}

class Question {
    private String question;
    private Map<String, String> options;
    private String correctAnswer;

    public Question(String question, Map<String, String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class Exam {
    private Map<Integer, Question> questions;
    private int currentQuestionNumber;
    private Map<Integer, String> userAnswers;

    public Exam(Map<Integer, Question> questions) {
        this.questions = questions;
        this.currentQuestionNumber = 1;
        this.userAnswers = new HashMap<>();
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQuestionNumber);
    }

    public void answerQuestion(String answer) {
        userAnswers.put(currentQuestionNumber, answer);
        currentQuestionNumber++;
    }

    public Map<Integer, String> getUserAnswers() {
        return userAnswers;
    }
}
