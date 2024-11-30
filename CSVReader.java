import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
    public static List<List<String>> read(String fileName) {
        List<List<String>> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));
                list.add(lineData);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
