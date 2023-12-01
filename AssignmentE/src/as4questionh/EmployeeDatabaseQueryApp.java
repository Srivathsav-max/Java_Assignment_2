package as4questionh;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextArea resultTextArea;

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

        resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        Button executeButton = new Button("Execute Query");
        executeButton.setOnAction(e -> executeQuery());

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(queryComboBox, executeButton, resultTextArea);

        primaryStage.setScene(new Scene(root, 400, 300));
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

                StringBuilder result = new StringBuilder();

                while (resultSet.next()) {
                    String socialSecurityNumber = resultSet.getString("socialSecurityNumber");

                    String employeeName = getEmployeeNameBySocialSecurityNumber(socialSecurityNumber);

                    result.append("Employee Name: ").append(employeeName).append("\n");
                }

                resultTextArea.setText(result.toString());

            } catch (SQLException e) {
                e.printStackTrace();
                resultTextArea.setText("Error executing query");
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
                return "SELECT * FROM hourlyEmployees WHERE hours > 30";
            case "Select all commission employees in descending order of the commission rate":
                return "SELECT * FROM commissionEmployees ORDER BY commissionRate DESC";
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
}
