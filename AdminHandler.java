package org.example;

import java.util.*;
import java.io.*;

public class AdminHandler {
    private static final String EMPLOYEE_FILE = "Employee.csv";  // employee data file
    private static final String SCALE_ID_FILE = "ScaleIDwDesc.csv"; // scale ID description file

    public static void handleAdminLogin(Scanner scanner) {
        CSVHandler employeeHandler = new CSVHandler(EMPLOYEE_FILE);
        Map<String, String[]> employeeData = employeeHandler.loadEmployeeData();

        System.out.println("Admin login:");
        boolean adminLoggedIn = false;

        // admin login loop
        while (!adminLoggedIn) {
            System.out.println("Please enter password: ");
            String password = scanner.nextLine().trim();
            if ("Password123".equals(password)) {
                adminLoggedIn = true;
                System.out.println("Hello Admin! What would you like to do?");
                displayEmployeeList(employeeData);
                handleAdminOptions(scanner, employeeData, employeeHandler);
            } else {
                System.out.println("Incorrect password.");
            }
        }
    }

    // display all employees
    private static void displayEmployeeList(Map<String, String[]> employeeData) {
        System.out.println("Employee List:");
        for (String id : employeeData.keySet()) {
            String[] details = employeeData.get(id);
            System.out.println("ID: " + details[0] + ", Name: " + details[1] + ", Scale ID: " + details[2] + ", Description: " + details[3]);
        }
    }

    // haandle admin options like adding or deleting employees
    private static void handleAdminOptions(Scanner scanner, Map<String, String[]> employeeData, CSVHandler employeeHandler) {
        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Exit");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addEmployee(scanner, employeeData, employeeHandler);
                    break;
                case "2":
                    deleteEmployee(scanner, employeeData, employeeHandler);
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // add new employee
    private static void addEmployee(Scanner scanner, Map<String, String[]> employeeData, CSVHandler employeeHandler) {
        System.out.println("Enter Employee Name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Enter Job Description (e.g., Senior Admin): ");
        String description = scanner.nextLine().trim();

        // find corresponding ScaleID prefxi from ScaleIDwDesc file
        String scalePrefix = getScaleIDPrefixFromDescription(description);

        if (scalePrefix != null) {
            // determine the available range for the ScaleID
            String scaleID = getNewScaleID(scalePrefix);
            String employeeID = generateEmployeeID();

            // create employee and add to data map
            String[] employeeDetails = {employeeID, name, scaleID, description, "No", "No", "No"};

            System.out.println("Is the employee married? (Yes/No): ");
            String isMarried = scanner.nextLine().trim();
            employeeDetails[4] = isMarried.equalsIgnoreCase("Yes") ? "Yes" : "No";

            System.out.println("Is the employee a single child carer? (Yes/No): ");
            String isChildCarer = scanner.nextLine().trim();
            employeeDetails[5] = isChildCarer.equalsIgnoreCase("Yes") ? "Yes" : "No";

            System.out.println("Is the employee the sole income earner? (Yes/No): ");
            String isSoleIncome = scanner.nextLine().trim();
            employeeDetails[6] = isSoleIncome.equalsIgnoreCase("Yes") ? "Yes" : "No";

            // add the updated employee details to the employee data map
            employeeData.put(employeeID, employeeDetails);

            // update the Employee.csv file with the new data
            employeeHandler.updateEmployeeData(employeeData);
            System.out.println("Employee added successfully with Scale ID: " + scaleID);
        } else {
            System.out.println("No matching job description found for Scale ID creation.");
        }
    }

    // delete employee by ID
    private static void deleteEmployee(Scanner scanner, Map<String, String[]> employeeData, CSVHandler employeeHandler) {
        System.out.println("Enter Employee ID to delete: ");
        String employeeID = scanner.nextLine().trim();

        if (employeeData.containsKey(employeeID)) {
            employeeData.remove(employeeID);
            employeeHandler.updateEmployeeData(employeeData);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee ID not found.");
        }
    }

    // get ScaleID prefix from description
    private static String getScaleIDPrefixFromDescription(String description) {
        // read ScaleIDwDesc.csv to get the prefix 
        try (BufferedReader br = new BufferedReader(new FileReader(SCALE_ID_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] scaleData = line.split(",");
                if (scaleData.length >= 3 && scaleData[2].equalsIgnoreCase(description)) {
                    return scaleData[0].split("_")[0] + "_" + scaleData[0].split("_")[1]; // Extract prefix (e.g., AD_SA)
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // determine the available scale range and prompt for scale number
    private static String getNewScaleID(String scalePrefix) {
        // check existing ScaleIDs in Employee.csv to determine the current range
        int maxScaleNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] employeeData = line.split(",");
                if (employeeData.length > 2 && employeeData[2].startsWith(scalePrefix)) {
                    String scaleID = employeeData[2];
                    int scaleNumber = Integer.parseInt(scaleID.split("_")[2]);
                    maxScaleNumber = Math.max(maxScaleNumber, scaleNumber);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // prompt the user for the scale number within the range
        Scanner scanner = new Scanner(System.in);
        System.out.println("Where on the payscale is this employee? (" + String.format("%02d", maxScaleNumber + 1) + " - " + String.format("%02d", maxScaleNumber + 10) + ")");
        String scaleNumber = scanner.nextLine().trim();

        return scalePrefix + "_" + String.format("%02d", Integer.parseInt(scaleNumber));
    }

    // generate Employee ID
    private static String generateEmployeeID() {
        Random rand = new Random();
        return String.format("%05d", rand.nextInt(100000));
    }
}
