package org.example;

import java.io.*;
import java.util.*;

public class CSVHandler {

    private String filePath;

    public CSVHandler(String filePath) {
        this.filePath = filePath;
    }

    // read the CSV file and return it as a list of rows
    public List<String[]> readFile() throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                rows.add(elements);
            }
        }
        return rows;
    }

    // write the rows back to the CSV file
    public void writeFile(List<String[]> rows) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        }
    }
}
