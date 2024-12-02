package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Payslip {
    List<List<String>> Scales = CSVReader.read("ScaleID.csv");
    List<List<String>> empcsv = CSVReader.read("Employee.csv");

    public Payslip() {
    }

    public void makePayslip(Employee emp) {
        double gross = this.gross(emp);
        double tax = this.IncomeTax(emp);
        double prsi = this.PRSI(gross);
        double usc = this.USC(gross);
        double forsa = this.FORSA(gross);
        double totalDeduction = tax + prsi + usc + forsa;
        double net = gross - totalDeduction;

        try {
            File csv = new File(emp.getId() + ".csv");
            FileWriter fw = new FileWriter(csv, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String first = "Id,Gross,Tax,PRSI,USC,FORSA,Total Deduction,Net";
            if (csv.length() == 0L) {
                bw.write(first);
                bw.newLine();
            }

            String var10000 = emp.getId();
            String line = var10000 + "," + (double)Math.round(gross * 1000.0) / 1000.0 + "," + (double)Math.round(tax * 1000.0) / 1000.0 + "," + (double)Math.round(prsi * 1000.0) / 1000.0 + "," + (double)Math.round(usc * 1000.0) / 1000.0 + "," + (double)Math.round(forsa * 1000.0) / 1000.0 + "," + (double)Math.round(totalDeduction * 1000.0) / 1000.0 + "," + (double)Math.round(net * 1000.0) / 1000.0;
            bw.write(line, 0, line.length());
            bw.newLine();
            bw.close();
        } catch (IOException var21) {
            throw new RuntimeException(var21);
        }
    }

    public double gross(Employee emp) {
        Iterator var2 = this.Scales.iterator();

        List<String> Scale;
        do {
            if (!var2.hasNext()) {
                return 0.0;
            }

            Scale = (List<String>) var2.next();
        } while (!emp.getScaleID().equals(Scale.get(0)));

        // ensure that the value is a valid integer before parsing
        String grossValueStr = Scale.get(1).trim();
        try {
            return Integer.parseInt(grossValueStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid gross value in CSV: " + grossValueStr);
            return 0.0; // return 0 if invalid
        }
    }


    public double PRSI(double notional) {
        double weekly = notional / 52.0;
        return weekly < 38.0 ? 0.0 : weekly * 0.041;
    }

    public double IncomeTax(Employee emp) {
        double gross = this.gross(emp);
        if (emp.isMarried()) {
            if (emp.isSoleIncome()) {
                return gross < 51000.0 ? gross * 0.2 : 10200.0 + (gross - 51000.0) * 0.4;
            } else {
                return gross < 51000.0 ? gross * 0.2 : 10200.0 + (gross - 51000.0) * 0.4;
            }
        } else if (emp.isSingleChildCarer()) {
            return gross < 46000.0 ? gross * 0.2 : 9200.0 + (gross - 46000.0) * 0.4;
        } else if (emp.isSingleChildCarer()) {
            return gross < 42000.0 ? gross * 0.2 : 8400.0 + (gross - 42000.0) * 0.4;
        } else {
            return 0.0;
        }
    }

    public double USC(double gross) {
        if (gross < 12012.0) {
            return gross * 0.005;
        } else if (gross < 25760.0) {
            return 60.06 + (gross - 12012.0) * 0.02;
        } else {
            return gross < 70044.0 ? 335.02 + (gross - 25760.0) * 0.04 : 2106.38 + (gross - 70044.0) * 0.08;
        }
    }

    public double FORSA(double gross) {
        if (gross < 18652.7) {
            return 7.82;
        } else if (gross < 40313.9) {
            return 16.07;
        } else if (gross < 56559.8) {
            return 24.07;
        } else if (gross < 84839.7) {
            return 34.3;
        } else {
            return gross < 105899.2 ? 37.12 : 40.61;
        }
    }
}
