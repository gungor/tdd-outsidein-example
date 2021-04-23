package tddexample.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "SEQ_EMPLOYEE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_EMPLOYEE", sequenceName = "SEQ_EMPLOYEE",allocationSize = 1)
    private Integer id;

    @Column(name="FULLNAME")
    private String fullName;

}
