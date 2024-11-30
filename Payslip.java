import java.util.List;

public class Payslip {
    List<List<String>> Scales = CSVReader.read("ScaleID.csv");
    public void makePayslip(Employee emp) {
        int salary;
        for (List<String> Scale : Scales) {
            if (Scale.get(1) == emp.getPayScale()) {
                    int pay = Integer.parseInt(Scale.get(2));
            }
        }
    }
}