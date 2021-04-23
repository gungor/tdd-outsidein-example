package tddexample.service;

import org.springframework.stereotype.Service;
import tddexample.model.entity.Employee;
import tddexample.model.rest.EmployeeSaveRequest;
import tddexample.model.rest.EmployeeUpdateRequest;

@Service
public class EmployeeService {

    public Employee saveEmployee(EmployeeSaveRequest request){
        return null;
    }

    public Employee updateEmployee(EmployeeUpdateRequest request) {
        return null;
    }

    public Employee getEmployee(Integer id) {
        return null;
    }

}
