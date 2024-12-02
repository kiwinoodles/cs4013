package org.example;

import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Please enter your role (Admin, HR, or User):");
                String role = scanner.nextLine().trim();

                // admin Login
                if ("Admin".equalsIgnoreCase(role)) {
                    AdminHandler.handleAdminLogin(scanner);
                    break; // exit the loop if admin login is successful
                }
                // HR Login
                else if ("HR".equalsIgnoreCase(role)) {
                    HRHandler.handleHRLogin(scanner);
                    break; // exit the loop if HR login is successful
                }
                // user Login
                else if ("User".equalsIgnoreCase(role)) {
                    UserHandler.handleUserLogin(scanner);
                    break; // exit the loop if User login is successful
                }
                // invalid role entered
                else {
                    System.out.println("Invalid role entered: Please try again!");
                }
            }
        }
    }
}
