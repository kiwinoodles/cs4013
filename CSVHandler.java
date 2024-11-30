import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CSVHandler {
    private final String filePath;

    // constructor with a default file path for Employee file
    public CSVHandler(String filePath) {
        this.filePath = filePath;
    }

    // reading a file into a HashMap with file path
    public Map<String, String[]> readFile(String filePath) throws IOException {
        Map<String, String[]> data = new HashMap<>(); // this will store the key-value pairs
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // split each line by commas
                if (values.length > 0) {
                    String key = values[0]; // assume the first column is the unique key
                    data.put(key, values); // store the key and the rest of the row
                }
            }
        } catch (IOException e) {
            throw new IOException("something went wrong while reading: " + e.getMessage(), e);
        }
        return data; // give back the whole hashmap
    }

    // Writing a new row to the file
    public void writeRow(String[] row) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", row)); // turn the array into a string separated by commas
            bw.newLine(); // make sure the next row starts on a new line
        } catch (IOException e) {
            throw new IOException("oops, error writing to file: " + e.getMessage(), e);
        }
    }

    // updating or adding a row in the file
    public void updateRow(String key, String[] newRow) throws IOException {
        Map<String, String[]> data = readFile(filePath); // read the current file into a hashmap
        data.put(key, newRow); // add or replace the row with the new data

        // rewrite the file with the updated data
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String[] row : data.values()) {
                bw.write(String.join(",", row)); // write each row back to the file
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IOException("oops, error writing to file: " + e.getMessage(), e);
        }
    }
}
