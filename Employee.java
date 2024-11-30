import java.util.List;

public class Employee {
    private String name = "Ashley Collier";
    private String Id = "51087";
    private String payScale = "EP_CM_08";
    private String Position = "EPS Category Manager";
    List<List<String>> emp = CSVReader.read("Employee.csv");

    public Employee(String var) {
        for (List<String> line : emp) {
            if(line.get(0).equals(var)) {
                this.Id = var;
                this.name = line.get(1);
                this.payScale = line.get(2);
                this.Position = line.get(3);
            } else if(line.get(1).equals(var)) {
                this.Id = line.get(0);
                this.name = var;
                this.payScale = line.get(2);
                this.Position = line.get(3);
            }
        }
    }
    
    public String getPayScale() {
        return payScale;
    }
}
