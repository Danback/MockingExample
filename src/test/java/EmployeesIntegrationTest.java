import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.BankService;
import com.example.Employee;
import com.example.EmployeeRepository;
import com.example.Employees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeesIntegrationTest {

    private EmployeeRepository employeeRepository;
    private BankService bankService;
    private Employees employees;

    @BeforeEach
    void setUp() {
        employeeRepository = new FakeEmployeeRepository();
        bankService = mock(BankService.class);
        employees = new Employees(employeeRepository, bankService);
    }

    @Test
    void payEmployees_WithFakeRepository() {
        Employee e1 = new Employee("1", 1000.0);
        Employee e2 = new Employee("2", 2000.0);
        employeeRepository.save(e1);
        employeeRepository.save(e2);

        when(bankService.pay(anyString(), anyDouble())).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Employee emp = employeeRepository.save(new Employee((String) args[0], (double) args[1]));
            emp.setPaid(true);
            return null;
        });

        int payments = employees.payEmployees();

        assertEquals(2, payments);
        assertTrue(e1.isPaid());
        assertTrue(e2.isPaid());
    }
}