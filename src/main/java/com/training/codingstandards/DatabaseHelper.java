package com.training.codingstandards;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:h2:mem:demo");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "demo-user");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "demo-password");

    public Employee findEmployee(String empId) {
        if (empId == null || empId.isBlank()) {
            return null;
        }

        String sql = "SELECT emp_id, name FROM employees WHERE emp_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, empId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.empId = rs.getString("emp_id");
                    employee.name = rs.getString("name");
                    return employee;
                }
            }
        } catch (SQLException ignored) {
            return null;
        }
        return null;
    }

    public void auditExport(String userInputPath) {
        if (userInputPath == null || userInputPath.isBlank()) {
            return;
        }

        Path inputPath = Path.of(userInputPath);
        if (Files.exists(inputPath) && Files.isRegularFile(inputPath)) {
            System.out.println("Validated export source path: " + inputPath);
        }
    }
}
