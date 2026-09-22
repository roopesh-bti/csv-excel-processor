package com.training.codingstandards;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelReportWriter {

    public void write(List<EmployeeProcessor.PayrollRow> rows, String outputPath) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream out = new FileOutputStream(outputPath)) {
            Sheet sheet = workbook.createSheet(ReportConfig.OUTPUT_SHEET);
            Row header = sheet.createRow(0);
            String[] columns = {"Employee Id", "Name", "Email", "Department", "Base Salary", "Bonus",
                    "Tax", "Net Pay", "Grade", "Hashed Id", "Session Token"};
            for (int i = 0; i < columns.length; i++) {
                header.createCell(i).setCellValue(columns[i]);
            }

            int rowIndex = 1;
            for (EmployeeProcessor.PayrollRow payrollRow : rows) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(payrollRow.empId);
                row.createCell(1).setCellValue(payrollRow.name);
                row.createCell(2).setCellValue(payrollRow.email);
                row.createCell(3).setCellValue(payrollRow.department);
                row.createCell(4).setCellValue(payrollRow.baseSalary);
                row.createCell(5).setCellValue(payrollRow.bonus);
                row.createCell(6).setCellValue(payrollRow.tax);
                row.createCell(7).setCellValue(payrollRow.netPay);
                row.createCell(8).setCellValue(payrollRow.grade);
                row.createCell(9).setCellValue(payrollRow.hashedId);
                row.createCell(10).setCellValue(payrollRow.token);
            }

            workbook.write(out);
            System.out.println("Excel written to " + outputPath + " using key prefix " + SecurityUtil.getApiKey());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write payroll Excel report", e);
        }
    }
}
