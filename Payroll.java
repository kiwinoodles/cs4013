import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Payroll {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask promo = new TimerTask() {
            public void run() {}
        };
        TimerTask pay = new TimerTask() {
            public void run() {}
        };
        Calendar promoDate = Calendar.getInstance();
        promoDate.set(Calendar.MONTH,Calendar.OCTOBER);
        promoDate.set(Calendar.DAY_OF_MONTH,1);
        timer.schedule(promo, promoDate.getTime());
        Calendar payDate = Calendar.getInstance();
        promoDate.set(Calendar.DAY_OF_MONTH,25);
        timer.schedule(pay, payDate.getTime());
    }
}

