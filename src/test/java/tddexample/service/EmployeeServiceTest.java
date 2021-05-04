package tddexample.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tddexample.model.entity.Employee;
import tddexample.model.rest.EmployeeSaveRequest;
import tddexample.repository.EmployeeRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldSaveEmployee(){
        Employee employee = new Employee(20, "Frodo Baggins");
        when(employeeRepository.save(new Employee(null, "Frodo Baggins")))
                .thenReturn(employee);

        EmployeeSaveRequest request = new EmployeeSaveRequest("Frodo Baggins");
        Employee savedEmployee = employeeService.saveEmployee(request);

        verify(employeeRepository,times(1)).save(any(Employee.class));
        Assertions.assertEquals(employee,savedEmployee);
    }

}
