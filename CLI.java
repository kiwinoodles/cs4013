package org.example;

import java.io.IOException;
import java.util.*;

public class CLI {
    public static void main(String[] args) {
        String filepath = "C://Users//hmiko//Documents//Employee.csv"; // path to the CSV file change accordingly
        CSVHandler editor = new CSVHandler(filepath);
        Map<String, String[]> dataMap = new HashMap<>(); // store all columns in a map

        try {
            List<String[]> dataList = editor.readFile();  // load data from CSV into a list
            // convert List<String[]> to Map<String, String[]>
            for (String[] row : dataList) {
                String id = row[0];  // assuming the first column is the employee ID
                dataMap.put(id, row); // store row in map with ID as the key
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading the file: " + e.getMessage());
            return; // exit if there's an error loading the file
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter your role (Admin, HR, or User):");
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
                        // display all employees here ONLY after admin login
                        System.out.println("Employee List:");
                        for (String id : dataMap.keySet()) {
                            String[] details = dataMap.get(id);
                            System.out.println("ID: " + details[0] + ", Name: " + details[1] + ", Scale ID: " + details[2] + ", Description: " + details[3]);
                        }
                        while (true) {
                            System.out.println("Choose an option: ");
                            System.out.println("1. Delete Employee");
                            System.out.println("2. Exit");

                            String choice = scanner.nextLine().trim();
                            switch (choice) {
                                case "1":
                                    System.out.println("Enter Employee ID to delete: ");
                                    String idToDelete = scanner.nextLine().trim();
                                    if (dataMap.containsKey(idToDelete)) {
                                        // remove the employee from the map and update the file
                                        dataMap.remove(idToDelete);
                                        List<String[]> updatedData = new ArrayList<>(dataMap.values()); // convert map back to list
                                        try {
                                            editor.writeFile(updatedData);  // update CSV file
                                        } catch (IOException e) {
                                            System.err.println("Error updating CSV: " + e.getMessage());
                                        }
                                        System.out.println("Employee deleted successfully.");
                                    } else {
                                        System.out.println("ID not found.");
                                    }
                                    break;
                                case "2":
                                    System.out.println("Goodbye!");
                                    return; // exit the loop and program
                                default:
                                    System.out.println("Invalid option. Please try again.");
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
                        // display all employees HERE after HR login
                        System.out.println("Employee List:");
                        for (String id : dataMap.keySet()) {
                            String[] details = dataMap.get(id);
                            System.out.println("ID: " + details[0] + ", Name: " + details[1] + ", Scale ID: " + details[2] + ", Description: " + details[3]);
                        }

                        // this part shows options like view id etc..
                        while (true) {
                            System.out.println("Choose an employee ID to view their details or exit:");
                            String idToView = scanner.nextLine().trim();
                            if (dataMap.containsKey(idToView)) {
                                String[] details = dataMap.get(idToView);
                                System.out.println("Now displaying employee: " + details[1]);
                                while (true) {
                                    System.out.println("Choose an option: ");
                                    System.out.println("1. View Scale ID");
                                    System.out.println("2. View Description");
                                    System.out.println("3. Exit");

                                    String choice = scanner.nextLine().trim();
                                    switch (choice) {
                                        case "1":
                                            System.out.println("Scale ID: " + details[2]);
                                            break;
                                        case "2":
                                            System.out.println("Description: " + details[3]);
                                            break;
                                        case "3":
                                            System.out.println("Exiting employee details view.");
                                            return; // Exit the employee view section
                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                    }
                                }
                            } else {
                                System.out.println("ID not found. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Incorrect password");
                    }
                }
            } else if ("User".equalsIgnoreCase(input)) {
                System.out.println("Proceeding as a User...");
                UserLogIn = true;
            } else {
                System.out.println("Invalid role entered: Proceeding to User");
                UserLogIn = true; // ensures that "User" is logged in
            }
        }
    }
}
