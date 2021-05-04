package tddexample.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import tddexample.model.entity.Employee;
import tddexample.model.rest.EmployeeSaveRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tddexample.model.rest.EmployeeUpdateRequest;
import tddexample.service.EmployeeService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJson
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void shouldAddEmployee() throws Exception {
        Employee employee = new Employee(null,"Frodo Baggins");
        EmployeeSaveRequest request = new EmployeeSaveRequest("Frodo Baggins");

        when(employeeService.saveEmployee(request)).thenReturn(employee);

        MvcResult result = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(employeeService,times(1)).saveEmployee(request);

        Employee savedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(),
                Employee.class );

        Assertions.assertEquals(employee.getFullName(),savedEmployee.getFullName());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        EmployeeUpdateRequest request = new EmployeeUpdateRequest(15,"Frodo Baggins");

        Employee employee = new Employee(request.getId(),"Frodo Baggins");
        when(employeeService.updateEmployee(request)).thenReturn(employee);

        MvcResult result = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content( objectMapper.writeValueAsString(request))
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(employeeService,times(1)).updateEmployee(request);

        Employee updatedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(),
                Employee.class );

        Assertions.assertEquals(employee.getFullName(),updatedEmployee.getFullName());
        Assertions.assertEquals(15,updatedEmployee.getId());
    }

    @Test
    public void shouldGetEmployeeWhenCalledWithExistingId() throws Exception {
        Employee employee = new Employee(20,"Frodo Baggins");
        when(employeeService.getEmployee(20)).thenReturn(employee);

        MvcResult result = mockMvc.perform(get("/employees/20")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(employeeService,times(1)).getEmployee(20);

        Employee savedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(),
                Employee.class );

        Assertions.assertEquals(employee.getFullName(),savedEmployee.getFullName());
        Assertions.assertEquals(20,savedEmployee.getId());
    }

    @Test
    public void shouldGetEmployeeWhenCalledWithExistingEmployeeName() throws Exception {
        Employee employee = new Employee(20,"FBaggins");
        when(employeeService.getEmployeeByName("FBaggins")).thenReturn(employee);

        MvcResult result = mockMvc.perform(get("/employees/name/FBaggins")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        verify(employeeService,times(1)).getEmployeeByName("FBaggins");

        Employee savedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(),
                Employee.class );

        Assertions.assertEquals(employee.getFullName(),savedEmployee.getFullName());
        Assertions.assertEquals(20,savedEmployee.getId());
    }

}
