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
        // Använder den fake implementationen av EmployeeRepository
        employeeRepository = new FakeEmployeeRepository();
        // Mockar BankService
        bankService = mock(BankService.class);
        // Skapar en instans av Employees med de mockade beroendena
        employees = new Employees(employeeRepository, bankService);
    }

    @Test
    void payEmployees_WithFakeRepository() {
        // Skapar två anställda och sparar dem i den fake repositoryn
        Employee e1 = new Employee("1", 1000.0);
        Employee e2 = new Employee("2", 2000.0);
        employeeRepository.save(e1);
        employeeRepository.save(e2);

        // Stubbar bankService.pay för att simulera att anställda blir betalda
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            String id = (String) args[0];
            Employee emp = ((FakeEmployeeRepository)employeeRepository).findById(id);
            if (emp != null) {
                emp.setPaid(true);
            }
            return null; // Returnera null eftersom det är en void-metod
        }).when(bankService).pay(anyString(), anyDouble());

        // Utför betalningsmetoden
        int payments = employees.payEmployees();

        // Verifierar att båda anställda har blivit markerade som betalda
        assertEquals(2, payments, "Both employees should have been paid.");
        assertTrue(e1.isPaid(), "Employee 1 should be marked as paid.");
        assertTrue(e2.isPaid(), "Employee 2 should be marked as paid.");
    }
}