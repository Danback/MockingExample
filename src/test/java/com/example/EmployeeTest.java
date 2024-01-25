package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void testEmployeeGettersAndSetters() {
        Employee employee = new Employee("1", 1000.0);
        employee.setPaid(true);

        assertEquals("1", employee.getId());
        assertEquals(1000.0, employee.getSalary());
        assertTrue(employee.isPaid());
    }
    @Test
    void testToString() {
        Employee employee = new Employee("123", 5000.0);
        String expected = "Employee [id=123, salary=5000.0]";
        assertEquals(expected, employee.toString());

    }

    @Test
    void testSetId() {
        // Skapa ett Employee-objekt med initialt id och salary
        Employee employee = new Employee("1", 1000.0);

        // Ändra id för Employee-objektet
        employee.setId("2");

        // Verifiera att id:t har ändrats som förväntat
        assertEquals("2", employee.getId(), "Employee id should be updated.");
    }

    @Test
    void testSetSalary() {
        // Skapa ett Employee-objekt med initialt id och salary
        Employee employee = new Employee("1", 1000.0);

        // Ändra salary för Employee-objektet
        employee.setSalary(2000.0);

        // Verifiera att salary har ändrats som förväntat
        assertEquals(2000.0, employee.getSalary(), 0.001, "Employee salary should be updated.");
    }
}