import java.util.*;

public class CLI {
    public static void main(String[] args) {
        List<List<String>> empcsv = CSVReader.read("Employeecsv");
        Timer timer = new Timer();
        TimerTask promo = new TimerTask() {
            public void run() {
                for (List<String> line : empcsv) {
                    Employee temp = new Employee(line.get(0));
                    temp.advance();
                }
            }
        };
        TimerTask pay = new TimerTask() {
            public void run() {
                for (List<String> line : empcsv) {
                    Employee temp = new Employee(line.get(0));
                    Payslip p = new Payslip();
                    p.makePayslip(temp);
                }
            }
        };
        Calendar promoDate = Calendar.getInstance();
        promoDate.set(Calendar.MONTH,Calendar.OCTOBER);
        promoDate.set(Calendar.DAY_OF_MONTH,1);
        timer.schedule(promo, promoDate.getTime());
        Calendar payDate = Calendar.getInstance();
        promoDate.set(Calendar.DAY_OF_MONTH,25);
        timer.schedule(pay, payDate.getTime());
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
