package as4questionh;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDatabaseQueryApp extends Application {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/employee";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    private Connection connection;

    private ComboBox<String> queryComboBox;
    private TableView<Employee> resultTable;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        connectToDatabase();

        queryComboBox = new ComboBox<>(FXCollections.observableArrayList(
                "Select all employees working in department SALES",
                "Select hourly employees working over 30 hours",
                "Select all commission employees in descending order of the commission rate"
        ));

        resultTable = new TableView<>();
        resultTable.setEditable(false);

        Button executeButton = new Button("Execute Query");
        executeButton.setOnAction(e -> executeQuery());

        TableColumn<Employee, String> employeeNameColumn = new TableColumn<>("Employee Name");
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Employee, Integer> hoursWorkedColumn = new TableColumn<>("Hours Worked");
        hoursWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

        TableColumn<Employee, Double> commissionRateColumn = new TableColumn<>("Commission Rate");
        commissionRateColumn.setCellValueFactory(new PropertyValueFactory<>("commissionRate"));

        resultTable.getColumns().addAll(employeeNameColumn, departmentColumn, hoursWorkedColumn, commissionRateColumn);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(queryComboBox, executeButton, resultTable);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Employee Database Query Application");
        primaryStage.show();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection error
        }
    }

    private void executeQuery() {
        String selectedQuery = queryComboBox.getValue();
        if (selectedQuery != null && !selectedQuery.isEmpty()) {
            try (PreparedStatement statement = connection.prepareStatement(buildQuery(selectedQuery));
                 ResultSet resultSet = statement.executeQuery()) {

                resultTable.getItems().clear(); // Clear previous results

                while (resultSet.next()) {
                    String socialSecurityNumber = resultSet.getString("socialSecurityNumber");

                    String employeeName = getEmployeeNameBySocialSecurityNumber(socialSecurityNumber);
                    String department = resultSet.getString("departmentName");
                    int hoursWorked = 0; // Default value for non-hourly employees
                    double commissionRate = 0.0; // Default value for non-commission employees

                    if (selectedQuery.equals("Select hourly employees working over 30 hours")) {
                        hoursWorked = resultSet.getInt("hours");
                    } else if (selectedQuery.equals("Select all commission employees in descending order of the commission rate")) {
                        commissionRate = resultSet.getDouble("commissionRate");
                    }

                    resultTable.getItems().add(new Employee(employeeName, department, hoursWorked, commissionRate));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle error
            }
        }
    }

    private String getEmployeeNameBySocialSecurityNumber(String socialSecurityNumber) {
        String query = "SELECT firstName, lastName FROM employees WHERE socialSecurityNumber = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, socialSecurityNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("firstName") + " " + resultSet.getString("lastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Employee not found";
    }

    private String buildQuery(String selectedQuery) {
        switch (selectedQuery) {
            case "Select all employees working in department SALES":
                return "SELECT * FROM employees WHERE departmentName = 'SALES'";
            case "Select hourly employees working over 30 hours":
                return "SELECT * FROM employees JOIN hourlyEmployees ON employees.socialSecurityNumber = hourlyEmployees.socialSecurityNumber WHERE hourlyEmployees.hours > 30";
            case "Select all commission employees in descending order of the commission rate":
                return "SELECT * FROM employees JOIN commissionEmployees ON employees.socialSecurityNumber = commissionEmployees.socialSecurityNumber ORDER BY commissionEmployees.commissionRate DESC";
            default:
                return "";
        }
    }

    @Override
    public void stop() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Employee {
        private final String employeeName;
        private final String department;
        private final int hoursWorked;
        private final double commissionRate;

        public Employee(String employeeName, String department, int hoursWorked, double commissionRate) {
            this.employeeName = employeeName;
            this.department = department;
            this.hoursWorked = hoursWorked;
            this.commissionRate = commissionRate;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public String getDepartment() {
            return department;
        }

        public int getHoursWorked() {
            return hoursWorked;
        }

        public double getCommissionRate() {
            return commissionRate;
        }
    }
}
