import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Payslip {
    List<List<String>> Scales = CSVReader.read("ScaleID.csv");

    public void makePayslip(Employee emp) {
        double gross = gross(emp);
        double tax = IncomeTax(emp);
        double prsi = PRSI(gross);
        double usc = USC(gross);
        double forsa = FORSA(gross);
        double totalDeduction = tax + prsi + usc + forsa;
        double net = gross - totalDeduction;
        LocalDate now = LocalDate.now();
        try {
            File csv = new File("" + emp.getId() + ".csv");
            FileWriter fw = new FileWriter(csv,true);
            BufferedWriter bw = new BufferedWriter(fw);
            String first = "Id,Gross,Tax,PRSI,USC,FORSA,Total Deduction,Net,Date Issued";
            if (csv.length() == 0) {
                bw.write(first);
                bw.newLine();
            }
            String line = emp.getId() + "," + ((double)Math.round(gross * 1000d) / 1000d) + "," + ((double)Math.round(tax * 1000d) / 1000d) + "," + ((double)Math.round(prsi * 1000d) / 1000d) + "," + ((double)Math.round(usc * 1000d) / 1000d) + "," + ((double)Math.round(forsa * 1000d) / 1000d) + "," + ((double)Math.round(totalDeduction * 1000d) / 1000d) + "," + ((double)Math.round(net * 1000d) / 1000d) + "," + now;
            bw.write(line,0,line.length());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double gross(Employee emp) {
        for (List<String> Scale : Scales) {
            if (emp.getPayScale().compareTo(Scale.get(0)) == 0) {
                return Integer.parseInt(Scale.get(1));
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