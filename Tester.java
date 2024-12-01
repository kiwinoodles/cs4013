public class Tester {
    public static void main(String[] args) {
        Employee emp = new Employee("51087");
        System.out.println(emp);
        Payslip p1 = new Payslip();
        p1.makePayslip(emp);
    }
}
