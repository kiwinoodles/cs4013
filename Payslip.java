import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Payslip {
    List<List<String>> Scales = CSVReader.read("ScaleID.csv");
    List<List<String>> empcsv = CSVReader.read("Employee.csv");

    public void makePayslip(Employee emp) {
        double gross = gross(emp);
        double tax = IncomeTax(emp);
        double prsi = PRSI(gross);
        double usc = USC(gross);
        double forsa = FORSA(gross);
        double totalDeduction = tax + prsi + usc + forsa;
        double net = gross - totalDeduction;
        try {
            File csv = new File("" + emp.getId() + ".csv");
            FileWriter fw = new FileWriter(csv);
            BufferedWriter bw = new BufferedWriter(fw);
            String first = "Gross,Tax,PRSI,USC,FORSA,Total Deduction,Net";
            if (csv.length() == 0) {
                bw.write(first);
            }
            
            bw.write("fadfad",0,6);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double gross(Employee emp) {
        for (List<String> Scale : Scales) {
            if (Scale.get(1) == emp.getPayScale()) {
                return Integer.parseInt(Scale.get(2));
            }
        }
        return 0;
    }

    public double PRSI(double notional) {
        double weekly = notional/52.0;
        if (weekly < 38.0) {
            return 0;
        } else {
            return weekly * 0.041;
        }
    }
    public double IncomeTax(Employee emp) {
        double gross = gross(emp);
        if (emp.isMarried()) {
            if (emp.isSoleIncome()) {
                if (gross < 51000.0) {
                    return gross * 0.2;
                } else {
                    return (51000.0 * 0.2) + (gross - 51000.0) * 0.4;
                }
            } else {
                if (gross < 51000.0) {
                    return gross * 0.2;
                } else {
                    return (51000.0 * 0.2) + (gross - 51000.0) * 0.4;
                }
            }
        } else {
            if (emp.isSingleChildCarer()) {
                if (gross < 46000.0) {
                    return gross * 0.2;
                } else {
                    return (46000.0 * 0.2) + (gross - 46000.0) * 0.4;
                }
            } else {
                if (emp.isSingleChildCarer()) {
                    if (gross < 42000.0) {
                        return gross * 0.2;
                    } else {
                        return (42000.0 * 0.2) + (gross - 42000.0) * 0.4;
                    }
                }
            }
        }
        return 0;
    }

    public double USC(double gross) {
        if (gross<12012.0) {
            return gross * 0.005;
        } else if (gross < 25760.0){
            return (12012.0 * 0.005) + (gross-12012.0) * 0.02;
        } else if (gross < 70044.0) {
            return (12012.0 * 0.005) + (13748.0 * 0.02) + (gross - 25760.0) * 0.04;
        } else {
            return (12012.0 * 0.005) + (13748.0 * 0.02) + (44284.0 * 0.04) + (gross - 70044.0) * 0.08;
        }
    }

    public double FORSA(double gross) {
        if (gross < 18652.70) {
            return 7.82;
        } else if (gross < 40313.90) {
            return 16.07;
        } else if (gross < 56559.80) {
            return 24.07;
        } else if (gross < 84839.70) {
            return 34.30;
        } else if (gross < 105899.20) {
            return 37.12;
        } else {
            return 40.61;
        }
    }
}