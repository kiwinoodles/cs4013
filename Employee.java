package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private String id;
    private String scaleID;
    private String description;
    private String married;
    private String singleChildCarer;
    private String soleIncome;

    // Constructor with the new fields
    public Employee(String name, String id, String scaleID, String description, String married, String singleChildCarer, String soleIncome) {
        this.name = name;
        this.id = id;
        this.scaleID = scaleID;
        this.description = description;
        this.married = married;
        this.singleChildCarer = singleChildCarer;
        this.soleIncome = soleIncome;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getScaleID() {
        return scaleID;
    }

    public String getDescription() {
        return description;
    }

    public String getMarried() {
        return married;
    }

    public String getSingleChildCarer() {
        return singleChildCarer;
    }

    public String getSoleIncome() {
        return soleIncome;
    }

    // Additional methods for convenience
    public boolean isMarried() {
        return "yes".equalsIgnoreCase(married); // Assuming "yes" means married
    }

    public boolean isSingleChildCarer() {
        return "yes".equalsIgnoreCase(singleChildCarer); // Assuming "yes" means single child carer

    public String toString() {
        return "" + Id + "," + name + "," + payScale + "," + Position + "," + Married + "," + SingleChildCarer + "," + SoleIncome;
    }

    public boolean isSoleIncome() {
        return "yes".equalsIgnoreCase(soleIncome); // Assuming "yes" means sole income
    }

    public void advance() {
        List<List<String>> Scales = CSVReader.read("ScaleID.csv");
        List<List<String>> empcsv = CSVReader.read("Employee.csv");
        boolean found = false;
        List<String> row = new ArrayList<String>();
        List<String> next = new ArrayList<String>();
        int count = 0;
        for (List<String> Scale : Scales) {
            if (found) {
                next = Scale;
                break;
            } else if (getPayScale().compareTo(Scale.get(0)) == 0) {
                row = Scale;
                found = true;
            }
            count++;
        }
        if (row.get(0).compareTo(next.get(0)) == -1) {
            try {
                FileWriter fw = new FileWriter("Employee.csv");
                BufferedWriter bw = new BufferedWriter(fw);
                int i = 0;
                for (List<String> line : empcsv) {
                    if (line.get(0).compareTo(Id) == 0) {
                        line.set(2, next.get(0));
                        empcsv.set(i, line);
                    }
                    i++;
                }
                for (List<String> line : empcsv) {
                    for (String s : line) {
                        bw.write(s + ",");
                    }
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("in");
            promote();
        }
    }

    public void promote() {
        List<List<String>> empcsv = CSVReader.read("Employee.csv");
        switch (getPosition()) {
            case "Administrator":
                Position = "Senior Administrator";
                payScale = "AD_SA_01";
                break;
            case "Professor":
                Position = "Full Professor";
                payScale = "AC_FP_01";
                break;
            case "Associate Professor A":
                Position = "Professor";
                payScale = "AC_PF_01";
                break;
            case "Associate Professor B":
                Position = "Professor";
                payScale = "AC_PF_01";
                break;
            default:
                System.out.println("No Promotion Available");
        }
        try {
            FileWriter fw = new FileWriter("Employee.csv");
            BufferedWriter bw = new BufferedWriter(fw);
            int i = 0;
            for (List<String> line : empcsv) {
                if (line.get(0).compareTo(Id) == 0) {
                    line.set(2, getPayScale());
                    line.set(3, getPosition());
                    empcsv.set(i, line);
                }
                i++;
            }
            for (List<String> line : empcsv) {
                for (String s : line) {
                    bw.write(s + ",");
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}