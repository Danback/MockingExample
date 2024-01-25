import com.example.BankService;
import com.example.Employee;
import com.example.EmployeeRepository;
import com.example.Employees;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


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

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            String id = (String) args[0];
            Employee emp = ((FakeEmployeeRepository)employeeRepository).findById(id);
            if (emp != null) {
                emp.setPaid(true);
            }
            return null;
        }).when(bankService).pay(anyString(), anyDouble());

        int payments = employees.payEmployees();

        assertEquals(2, payments, "Both employees should have been paid.");
        assertTrue(e1.isPaid(), "Employee 1 should be marked as paid.");
        assertTrue(e2.isPaid(), "Employee 2 should be marked as paid.");
    }
}