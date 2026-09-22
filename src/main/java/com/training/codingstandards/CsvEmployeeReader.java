package com.training.codingstandards;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvEmployeeReader {

    public List<Employee> read(String csvPath) {
        List<Employee> employees = new ArrayList<>();
        try (InputStream inputStream = loadCsvStream(csvPath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)) {

            for (CSVRecord record : parser) {
                Employee employee = new Employee();
                employee.empId = record.get("empId");
                employee.name = record.get("name");
                employee.email = record.get("email");
                employee.department = record.get("department");
                employee.salary = Double.parseDouble(record.get("salary"));
                employee.yearsOfService = Integer.parseInt(record.get("yearsOfService"));
                employee.country = record.get("country");
                employee.managerEmail = record.get("managerEmail");
                employees.add(employee);
                ReportConfig.CACHE.add(employee);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Unable to read employees from CSV", e);
        }
        return employees;
    }

    private InputStream loadCsvStream(String csvPath) throws IOException {
        if (csvPath == null) {
            InputStream resourceStream = CsvEmployeeReader.class.getResourceAsStream("/employees.csv");
            if (resourceStream == null) {
                throw new IOException("Unable to find default employees.csv resource");
            }
            return resourceStream;
        }
        return new FileInputStream(csvPath);
    }
}
