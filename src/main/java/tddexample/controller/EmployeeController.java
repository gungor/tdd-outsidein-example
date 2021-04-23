package tddexample.controller;

import org.springframework.web.bind.annotation.*;
import tddexample.model.entity.Employee;
import tddexample.model.rest.EmployeeSaveRequest;
import tddexample.model.rest.EmployeeUpdateRequest;
import tddexample.service.EmployeeService;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody @Valid EmployeeSaveRequest request) {
        return employeeService.saveEmployee(request);
    }

    @PutMapping("/employees")
    Employee updateEmployee(@RequestBody @Valid EmployeeUpdateRequest request) {
        return employeeService.updateEmployee(request);
    }

    @GetMapping("/employees/{id}")
    Employee getEmployee(@PathVariable Integer id) {
        return employeeService.getEmployee(id);
    }



}
