package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;

class EmployeesTest {

    private EmployeeRepository employeeRepository;
    private BankService bankService;
    private Employees employees;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        bankService = mock(BankService.class);
        employees = new Employees(employeeRepository, bankService);
    }

    @Test
    void payEmployees_AllPaymentsSuccessful() {
        List<Employee> allEmployees = Arrays.asList(new Employee("1", 1000.0), new Employee("2", 2000.0));
        when(employeeRepository.findAll()).thenReturn(allEmployees);

        employees.payEmployees();

        verify(bankService, times(2)).pay(anyString(), anyDouble());
        for (Employee employee : allEmployees) {
            assertTrue(employee.isPaid());
        }
    }

    @Test
    void payEmployees_PaymentFailsForOneEmployee() {
        Employee e1 = new Employee("1", 1000.0);
        Employee e2 = new Employee("2", 2000.0);
        List<Employee> allEmployees = Arrays.asList(e1, e2);
        when(employeeRepository.findAll()).thenReturn(allEmployees);

        // Anta att betalningen lyckas för den första anställda och misslyckas för den andra
        doNothing().when(bankService).pay(e1.getId(), e1.getSalary());
        doThrow(new RuntimeException("Payment failed")).when(bankService).pay(e2.getId(), e2.getSalary());

        int payments = employees.payEmployees();

        verify(bankService).pay(e1.getId(), e1.getSalary()); // Verifierar att bankService.pay kallas för e1
        verify(bankService).pay(e2.getId(), e2.getSalary()); // Försöker kalla bankService.pay för e2, men kastar ett undantag
        assertTrue(e1.isPaid());
        assertFalse(e2.isPaid());
        assertEquals(1, payments); // Endast en anställd fick betalt
    }
}