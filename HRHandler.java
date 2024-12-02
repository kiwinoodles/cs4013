import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
            System.out.println("4. Promote");
            System.out.println("5. Exit");

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
                    Employee employee = new Employee(idToView);
                    displayPayslipHistory(employee);
                    break;
                case "4":
                    promoteEmployee(idToView,dataMap);
                case "5":
                    System.out.println("Exiting employee details view.");
                    return; // exit the employee details view section
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayPayslipHistory(Employee emp) {
        File open = new File(emp.getId() + ".csv");
        List<List<String>> empcsv = CSVReader.read(emp.getId() + ".csv");
        for (List<String> row : empcsv) {
            System.out.println("Id:" + row.get(0) + "\nGross" + row.get(1) + "\nTax" + row.get(2) + "\nPRSI" + row.get(3) + "\nUSC" + row.get(4) + "\nFORSA" + row.get(5) + "\nTotal Deduction" + row.get(6) + "\nNet" + row.get(7) + "\nDate Issued");
        }
    }
    public static void promoteEmployee(String idToPromote, Map<String, String[]> dataMap) {

        while (true) {
            System.out.println("Enter UserID you would like to promote: ");

            if ("exit".equalsIgnoreCase(idToPromote)) {
                System.out.println("Exiting HR session.");
                break; // exit HR session if user types "exit"
            }
            if (dataMap.containsKey(idToPromote)) {
                Employee Testing = new Employee(idToPromote);
                Testing.promote();
                System.out.println("Sucessfully updated " + Testing);
                break;
            }
        }
    }
}
