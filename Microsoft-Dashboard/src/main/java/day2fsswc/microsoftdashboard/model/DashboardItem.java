package day2fsswc.microsoftdashboard.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DashboardItem {

    private final StringProperty name;
    private final StringProperty department;
    private final StringProperty status;
    private final StringProperty date;
    private final StringProperty value;

    public DashboardItem(String name, String department, String status, String date, String value) {
        this.name       = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.status     = new SimpleStringProperty(status);
        this.date       = new SimpleStringProperty(date);
        this.value      = new SimpleStringProperty(value);
    }

    public String getName()            { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getDepartment()             { return department.get(); }
    public StringProperty departmentProperty() { return department; }

    public String getStatus()            { return status.get(); }
    public StringProperty statusProperty() { return status; }

    public String getDate()            { return date.get(); }
    public StringProperty dateProperty() { return date; }

    public String getValue()            { return value.get(); }
    public StringProperty valueProperty() { return value; }
}
