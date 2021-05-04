package tddexample.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tddexample.exception.EmployeeNotFoundException;
import tddexample.model.entity.Employee;
import tddexample.model.rest.EmployeeSaveRequest;
import tddexample.model.rest.EmployeeUpdateRequest;
import tddexample.repository.EmployeeRepository;

import java.util.Optional;

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

    @Test
    public void shouldUpdateEmployee(){
        Employee oldEmployee = new Employee(20, "F. Baggins");
        Employee employee = new Employee(20, "Frodo Baggins");
        when(employeeRepository.findById(20)).thenReturn(Optional.of(oldEmployee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeUpdateRequest request = new EmployeeUpdateRequest(20,"Frodo Baggins");
        Employee updatedEmployee = employeeService.updateEmployee(request);

        verify(employeeRepository,times(1)).findById(any(Integer.class));
        verify(employeeRepository,times(1)).save(any(Employee.class));
        Assertions.assertEquals( updatedEmployee, employee );
    }

    @Test
    public void shouldThrowEmployeeNotFoundExceptionFromUpdateWhenIdNotExists(){
        EmployeeUpdateRequest request = new EmployeeUpdateRequest(1,"Frodo Baggins");
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateEmployee(request));

        verify(employeeRepository,times(1)).findById(any(Integer.class));
        verify(employeeRepository,times(0)).save(any(Employee.class));
    }

    @Test
    public void shouldGetEmployeeWhenIdExists(){
        Employee employee = new Employee(20, "Frodo Baggins");
        when(employeeRepository.findById(20)).thenReturn(Optional.of(employee));

        Assertions.assertEquals(employee, employeeService.getEmployee(20));
        verify(employeeRepository,times(1)).findById(any(Integer.class));
    }

    @Test
    public void shouldThrowEmployeeNotFoundExceptionFromGetWhenIdNotExist(){
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployee(1));

        verify(employeeRepository,times(1)).findById(any(Integer.class));
    }

    @Test
    public void shouldReturnEmployeeWhenFoundByName(){
        Employee employee = new Employee(10,"Frodo Baggins");
        when(employeeRepository.findByFullName("Frodo Baggins")).thenReturn(Optional.of(employee));

        Employee employeeFoundByName = employeeService.getEmployeeByName("Frodo Baggins");

        verify(employeeRepository,times(1)).findByFullName("Frodo Baggins");
        Assertions.assertEquals(employee,employeeFoundByName);
    }

}
