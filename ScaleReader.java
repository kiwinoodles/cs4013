import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScaleReader {
    public static void main(String[] args) {
        try {
            List<List<String>> data = new ArrayList<>(); // list of lists to store data
            String file = "ScaleID.csv"; // replace with the path to your own CSV file
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));
                data.add(lineData);
                line = br.readLine();
            }

            for (List<String> list : data) {
                for (String str : list) {
                    
                }
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
