package org.example;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Please enter your role (Admin, HR, or User):");
                String role = scanner.nextLine().trim();

                // Admin Login
                if ("Admin".equalsIgnoreCase(role)) {
                    AdminHandler.handleAdminLogin(scanner);
                    break; // Exit the loop if Admin login is successful
                }
                // HR Login
                else if ("HR".equalsIgnoreCase(role)) {
                    HRHandler.handleHRLogin(scanner);
                    break; // Exit the loop if HR login is successful
                }
                // User Login
                else if ("User".equalsIgnoreCase(role)) {
                    UserHandler.handleUserLogin(scanner);
                    break; // Exit the loop if User login is successful
                }
                // Invalid role entered
                else {
                    System.out.println("Invalid role entered: Please try again!");
                }
            }
        }
    }
}
