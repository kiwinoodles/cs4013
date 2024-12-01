package org.example;

import java.util.Map;
import java.util.Scanner;

public class UserHandler {
    public static void handleUserLogin(Scanner scanner) {
        System.out.println("User login:");

        // prompt user for their name and user ID
        System.out.println("Enter your Name_Surname (e.g., John_Doe):");
        String userNameInput = scanner.nextLine().trim();
        String userName = userNameInput.replace("_", " "); // replaces underscores with spaces

        System.out.println("Enter your User ID (password):");
        String userId = scanner.nextLine().trim();

        // ceate an instance of CSVHandler to load employee data
        String filepath = "Employee.csv"; // the path to your CSV file
        CSVHandler csvHandler = new CSVHandler(filepath);
        Map<String, String[]> employeeData = csvHandler.loadEmployeeData();

        // retrieve the employee by ID
        Employee user = getEmployeeById(userId, employeeData);

        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
            Payslip payslip = new Payslip();

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. View Payslip");
                System.out.println("2. Exit");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        // generate and display the payslip
                        payslip.makePayslip(user);
                        displayPayslip(user); // display payslip details
                        break;
                    case "2":
                        System.out.println("Goodbye!");
                        return; // exit the  session
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid login credentials. Please try again.");
        }
    }

    // tis method gets the employee by their ID from the loaded employee data
    private static Employee getEmployeeById(String userId, Map<String, String[]> employeeData) {
        if (employeeData.containsKey(userId)) {
            String[] details = employeeData.get(userId);
            // create and return the Employee object basd on the details in the CSV
            return new Employee(details[1], details[0], details[2], details[3], details[4], details[5], details[6]);
        }
        return null; // return null if no employee with the given ID is found
    }

    private static void displayPayslip(Employee emp) {
        Payslip payslip = new Payslip();
        double gross = payslip.gross(emp);
        double tax = payslip.IncomeTax(emp);
        double prsi = payslip.PRSI(gross);
        double usc = payslip.USC(gross);
        double forsa = payslip.FORSA(gross);
        double totalDeduction = tax + prsi + usc + forsa;
        double net = gross - totalDeduction;

        System.out.println("-----------------------------------");
        System.out.println("Payslip for Employee: " + emp.getName());
        System.out.println("Gross Pay: €" + String.format("%.2f", gross));
        System.out.println("Income Tax: €" + String.format("%.2f", tax));
        System.out.println("PRSI: €" + String.format("%.2f", prsi));
        System.out.println("USC: €" + String.format("%.2f", usc));
        System.out.println("FORSA: €" + String.format("%.2f", forsa));
        System.out.println("Total Deductions: €" + String.format("%.2f", totalDeduction));
        System.out.println("Net Pay: €" + String.format("%.2f", net));
        System.out.println("-----------------------------------");
    }
}
