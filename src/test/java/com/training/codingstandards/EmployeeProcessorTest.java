package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeProcessorTest {

    @Test
    void processCreatesPayrollRowForEachEmployee() {
        Employee employee = new Employee("1001", "Asha Raman", "asha.raman@example.com",
                "Engineering", 92000, 6, "IN", "lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertEquals("1001", rows.get(0).empId);
        assertEquals("Engineering", rows.get(0).department);
    }

    @Test
    void processUsesValueBasedStringComparisonForEngineeringBonus() {
        Employee employee = new Employee("1002", "Rohan Shah", "rohan.shah@example.com",
                new String("Engineering"), 120000, 12, new String("SG"), "lead.eng@example.com");

        EmployeeProcessor processor = new EmployeeProcessor();
        List<EmployeeProcessor.PayrollRow> rows = processor.process(Arrays.asList(employee));

        assertNotNull(rows);
        assertEquals(1, rows.size());
        assertTrue(rows.get(0).bonus > 0.0);
    }

    @Test
    void isAdminAcceptsTheConfiguredPassword() {
        assertEquals("Admin@12345", SecurityUtil.getAdminPassword());
        assertEquals(true, SecurityUtil.isAdmin(new String("Admin@12345")));
    }
}
