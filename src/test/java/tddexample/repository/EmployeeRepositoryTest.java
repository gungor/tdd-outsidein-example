package tddexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import tddexample.model.entity.Employee;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldReturnEmployeeByName(){
        testEntityManager.persistAndFlush(new Employee(null,"Frodo Baggins"));

        Employee employee = employeeRepository.findByFullName("Frodo Baggins").get();
        Assertions.assertEquals("Frodo Baggins",employee.getFullName());
    }

}
