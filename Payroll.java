package org.example;

// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Payroll {
    public Payroll() {
    }

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
        promoDate.set(2, 9);
        promoDate.set(5, 1);
        timer.schedule(promo, promoDate.getTime());
        Calendar payDate = Calendar.getInstance();
        promoDate.set(5, 25);
        timer.schedule(pay, payDate.getTime());
    }
}
