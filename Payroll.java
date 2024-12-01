package org.example;

// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Payroll {
    public Payroll() {
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask promo = new Payroll$1();
        TimerTask pay = new Payroll$2();
        Calendar promoDate = Calendar.getInstance();
        promoDate.set(2, 9);
        promoDate.set(5, 1);
        timer.schedule(promo, promoDate.getTime());
        Calendar payDate = Calendar.getInstance();
        promoDate.set(5, 25);
        timer.schedule(pay, payDate.getTime());
    }
}
