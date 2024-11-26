import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        String filepath = "src/main/resources/Employee.csv"; //Path to the CSV file
        Map<String, String[]> dataMap = new HashMap<>(); // Store all columns in an array

        // Load the CSV data into the map
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // Split each line into columns so text can be processed
                if (columns.length >= 4) { // Ensure there are at least four columns(change int for desired columns)
                    String id = columns[0].trim(); // First column of CSV file
                    String name = columns[1].trim(); // Second column...
                    String scaleID = columns[2].trim(); // ...
                    String description = columns[3].trim(); // ...

                    //Stores details from CSV into string format
                    dataMap.put(id, new String[]{name, scaleID, description});
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            return; // Exit if there's an error loading the file
        }
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter if you are an Admin, HR or a UserID");
            String input = scanner.nextLine().trim();
            boolean AdminLogIn = false;
            boolean HRLogIn = false;
            boolean UserLogIn = false;
            if ("Admin".equalsIgnoreCase(input)) {
                while (!AdminLogIn) {
                    System.out.println("Please enter password: ");

                    String password = scanner.nextLine().trim();
                    if ("Password123".equals(password)) {
                        AdminLogIn = true;
                        System.out.println("Hello Admin! What would you like to do?");
                        while (true) {// loop to allow interface to work
                            System.out.println("Choose an option: ");
                            System.out.println(" ");
                            System.out.println("Test 1");
                            System.out.println("Test 2");
                            System.out.println("Exit");

                            String choice = scanner.nextLine().trim();
                            switch (choice) {
                                case "Test 1": // add new "Case", String after case is what needs to be typed to get the output below the case
                                    System.out.println("Test 1 successful");
                                    System.out.println(" ");
                                    break;
                                case "Test 2":
                                    System.out.println("Test 2 successful");
                                    System.out.println(" ");
                                    break; // breaks loop and returns to the start of the loop
                                case "Exit":
                                    System.out.println("Goodbye!");
                                    System.out.println(" ");
                                    return; // Exit the loop and program
                                default:
                                    System.out.println("Invalid option. Please try again.");
                                    System.out.println(" ");
                            }
                        }
                    } else {
                        System.out.println("Incorrect password");
                    }
                }
            } else if ("HR".equalsIgnoreCase(input)) {
                while (!HRLogIn) {
                    System.out.println("Hello HR, please enter password: ");
                    String password2 = scanner.nextLine().trim();
                    if ("Password321".equals(password2)) {
                        System.out.println("Hello HR!");
                        HRLogIn = true;
                    } else {
                        System.out.println("Incorrect password");
                    }
                }
            } else if ("User".equalsIgnoreCase(input)) {
                System.out.println("Proceeding as a User...");
                UserLogIn = true;
            } else {
                System.out.println("Invalid role entered: Proceeding to User");
                UserLogIn = true; // ensures that "User" is logged in, to allow the correct code to run
            }


            System.out.println("Please enter user ID");
            input = scanner.nextLine().trim();

            if (dataMap.containsKey(input)) { // if the correct ID is entered
                String[] details = dataMap.get(input);
                System.out.println("Hello there: " + details[0] + ", What would you like to do?");
                while (true) {
                        System.out.println("Choose an option: ");
                        System.out.println(" ");
                        System.out.println("Scale ID");
                        System.out.println("Description");
                        System.out.println("Exit");

                        String choice = scanner.nextLine().trim();
                        switch (choice) {
                            case "Scale ID": // add new "Case", String after case is what needs to be typed to get the output below the case
                                System.out.println("Scale ID: " + details[1]);
                                System.out.println(" ");
                                break;
                            case "Description":
                                System.out.println("Description: " + details[2]);
                                System.out.println(" ");
                                break;
                            case "Exit":
                                System.out.println("Goodbye!");
                                System.out.println(" ");
                                return; // Exit the loop and program
                            default:
                                System.out.println("Invalid option. Please try again.");
                                System.out.println(" ");
                        }
                    }
                } else { //If ID is not found quits program
                    System.out.println("ID not found in the CSV file.");
                }

            }
        }
    }

