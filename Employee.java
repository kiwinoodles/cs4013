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
        List<List<String>> emp = CSVReader.read("Employee.csv");
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
        return name + Id + payScale + Position + Married + SingleChildCarer + SoleIncome;
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
}
