package org.example;

import java.io.*;
import java.util.*;

public class CSVHandler {
    private String filepath;

    public CSVHandler(String filepath) {
        this.filepath = filepath;
    }

    // LoadS employee data from Employee.csv
    public Map<String, String[]> loadEmployeeData() {
        Map<String, String[]> dataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // skip rows with missing data or improper formatting
                if (data.length < 4) {
                    continue;
                }
                dataMap.put(data[0], data); // employee ID as key
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    // load scale data from ScaleIDwDesc.csv
    public Map<String, String[]> loadScaleData() {
        Map<String, String[]> scaleDataMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("ScaleIDwDesc.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // skip rows with missing data or improper formatting
                if (data.length < 3) {
                    continue;
                }
                scaleDataMap.put(data[0], data); // scale ID as key
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaleDataMap;
    }

    // update employee data in Employee.csv
    public void updateEmployeeData(Map<String, String[]> dataMap) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String id : dataMap.keySet()) {
                String[] details = dataMap.get(id);
                bw.write(String.join(",", details));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method to get the highest number associated with a ScaleID prefix from ScaleIDwDesc
    public int getHighestScaleIDNumber(String scalePrefix) {
        int highestNumber = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("ScaleIDwDesc.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String scaleID = data[0];
                    if (scaleID.startsWith(scalePrefix)) {
                        String[] parts = scaleID.split("_");
                        int number = Integer.parseInt(parts[2]);
                        if (number > highestNumber) {
                            highestNumber = number;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highestNumber;
    }

    // method to generate employee ID
    public String generateUniqueEmployeeID(Map<String, String[]> employeeData) {
        Random rand = new Random();
        String employeeID;
        do {
            employeeID = String.format("%05d", rand.nextInt(100000));
        } while (employeeData.containsKey(employeeID)); // Ensure no duplicates
        return employeeID;
    }
}
