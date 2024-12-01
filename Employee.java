package org.example;

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
    }

    public boolean isSoleIncome() {
        return "yes".equalsIgnoreCase(soleIncome); // Assuming "yes" means sole income
    }
}
