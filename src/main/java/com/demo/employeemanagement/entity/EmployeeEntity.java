package com.demo.employeemanagement.entity;

import com.demo.employeemanagement.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Long aadhaar;
    private Integer age;
    private String department;
    private String city;
    private LocalDate dob;

    public static EmployeeDTO toDTO(EmployeeEntity employee) {
        return new EmployeeDTO(
                employee.id,
                employee.name,
                employee.aadhaar,
                employee.age,
                employee.department,
                employee.city,
                employee.dob
        );
    }


}

