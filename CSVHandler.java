package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
public class CSVHandler {
    private static String filePath;

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
                    String key = values[0].trim();
                    String name = values[1].trim(); // Second column...
                    String scaleID = values[2].trim(); // ...
                    String description = values[3].trim();// assume the first column is the unique key
                    data.put(key, new String[]{name, scaleID, description}); // store the key and the rest of the row
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
    public static void DeleteRow(String[] row) throws IOException {
        List<String> Temp = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Select UserID to delete: ");
            String valueToDelete = scanner.nextLine().trim();

            Temp = new ArrayList<>();
            boolean IsDeleted = false;
            try (Scanner FileScanner = new Scanner(new File(filePath))) {
                while (FileScanner.hasNextLine()) {
                    String CurrentLine = FileScanner.nextLine();
                    String[] values = CurrentLine.split(",");
                    if ((values.length > 0 && values[0].trim().equals(valueToDelete))) {
                        IsDeleted = true; // Mark that the row was found and deleted
                        continue;
                    }
                    Temp.add(CurrentLine);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : Temp) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (IsDeleted) {
                System.out.println("The User " + valueToDelete + "has been successfully deleted");
            } else {
                System.out.println("No matching ID has been found");
            }

        }

    }

    // updating or adding a row in the file
    public void updateRow(String[] newRow) throws IOException {
        Map<String, String[]> data = readFile(filePath); // read the current file into a hashmap
        // add or replace the row with the new data

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

