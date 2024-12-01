import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private String Id;
    private String payScale;
    private String Position;
    private boolean Married = false;
    private boolean SingleChildCarer = false;
    private boolean SoleIncome = false;

    public Employee(String var) {
        List<List<String>> emp = CSVReader.read("Employeetemp.csv");
        for (List<String> line : emp) {
            if(line.get(0).equals(var)) {
                this.Id = var;
                this.name = line.get(1);
                this.payScale = line.get(2);
                this.Position = line.get(3);
                this.Married = Boolean.parseBoolean(YesNo(line.get(4)));
                this.SingleChildCarer = Boolean.parseBoolean(YesNo(line.get(5)));
                this.SoleIncome = Boolean.parseBoolean(YesNo(line.get(6)));
            } else if(line.get(1).equals(var)) {
                this.Id = line.get(0);
                this.name = var;
                this.payScale = line.get(2);
                this.Position = line.get(3);
                this.Married = Boolean.parseBoolean(YesNo(line.get(4)));
                this.SingleChildCarer = Boolean.parseBoolean(YesNo(line.get(5)));
                this.SoleIncome = Boolean.parseBoolean(YesNo(line.get(6)));
            }
        }
    }

    public String getName() {
        return this.name;
    }
    public String getId() {
        return this.Id;
    }

    public String getPayScale() {
        return payScale;
    }

    public String getPosition() {
        return Position;
    }

    public boolean isMarried() {
        return Married;
    }

    public boolean isSingleChildCarer() {
        return SingleChildCarer;
    }

    public boolean isSoleIncome() {
        return SoleIncome;
    }

    public String toString() {
        return "" + Id + "," + name + "," + payScale + "," + Position + "," + Married + "," + SingleChildCarer + "," + SoleIncome;
    }
    public String YesNo(String var) {
        if (var.equals("Yes")) {
            return "True";
        } else if(var.equals("No")) {
            return "False";
        } else {
            return "Error";
        }
    }

    public void advance() {
        List<List<String>> Scales = CSVReader.read("ScaleID.csv");
        List<List<String>> empcsv = CSVReader.read("Employeetemp.csv");
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
        System.out.println(row.get(0).compareTo(next.get(0)));
        if (row.get(0).compareTo(next.get(0)) == -1) {
            System.out.println("in");
            try {
                FileWriter fw = new FileWriter("Employeetemp.csv");
                BufferedWriter bw = new BufferedWriter(fw);
                int i = 0;
                for (List<String> line : empcsv) {
                    System.out.println(i);
                    if (line.get(0).compareTo(Id) == 0) {
                        line.set(2, next.get(0));
                        empcsv.set(i, line);
                    }
                    i++;
                }
                for (List<String> line : empcsv) {
                    System.out.println(line);
                    bw.write(line.toString());
                    bw.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void promote() {

    }

}
