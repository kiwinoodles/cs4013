package org.example;

import java.util.Map;
import java.util.Scanner;

public class HRHandler {
    public static void handleHRLogin(Scanner scanner) {
        String filepath = "Employee.csv"; // path to the CSV file
        CSVHandler editor = new CSVHandler(filepath);
        Map<String, String[]> dataMap = editor.loadEmployeeData();

        System.out.println("HR login:");
        boolean HRLogIn = false;
        while (!HRLogIn) {
            System.out.println("Hello HR, please enter password: ");
            String password = scanner.nextLine().trim();
            if ("Password321".equals(password)) {
                HRLogIn = true;
                System.out.println("Hello HR!");
                // display all employees here after HR login
                displayEmployeeList(dataMap);
                handleHROptions(scanner, dataMap, editor);
            } else {
                System.out.println("Incorrect password.");
            }
        }
    }

    private static void displayEmployeeList(Map<String, String[]> dataMap) {
        System.out.println("Employee List:");
        for (String id : dataMap.keySet()) {
            String[] details = dataMap.get(id);
            System.out.println("ID: " + details[0] + ", Name: " + details[1] + ", Scale ID: " + details[2] + ", Description: " + details[3]);
        }
    }

    private static void handleHROptions(Scanner scanner, Map<String, String[]> dataMap, CSVHandler editor) {
        while (true) {
            System.out.println("Enter an Employee ID to view their details or 'exit'");
            String idToView = scanner.nextLine().trim();

            if ("exit".equalsIgnoreCase(idToView)) {
                System.out.println("Exiting HR session.");
                break; // exit HR session if user types "exit"
            }

            if (dataMap.containsKey(idToView)) {
                String[] details = dataMap.get(idToView);
                System.out.println("Now displaying employee: " + details[1]);
                handleHRDetailOptions(scanner, details, idToView, dataMap);
            } else {
                System.out.println("ID not found. Please try again.");
            }
        }
    }

    private static void handleHRDetailOptions(Scanner scanner, String[] details, String idToView, Map<String, String[]> dataMap) {
        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. View Scale ID");
            System.out.println("2. View Description");
            System.out.println("3. View Payslip");
            System.out.println("4. Exit");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println("Scale ID: " + details[2]); // using getScaleID()
                    break;
                case "2":
                    System.out.println("Description: " + details[3]);
                    break;
                case "3":
                    // generate and display the payslip
                    Employee employee = new Employee(details[1], idToView, details[2], details[3], details[4], details[5], details[6]);
                    displayPayslip(employee);
                    break;
                case "4":
                    System.out.println("Exiting employee details view.");
                    return; // exit the employee details view section
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
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
