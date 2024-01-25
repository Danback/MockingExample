import com.example.Employee;
import com.example.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FakeEmployeeRepository implements EmployeeRepository {
    private final Map<String, Employee> employees = new HashMap<>();

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Employee save(Employee e) {
        employees.put(e.getId(), e);
        return e;
    }

    public Employee findById(String id) {
        return employees.get(id);
    }
}