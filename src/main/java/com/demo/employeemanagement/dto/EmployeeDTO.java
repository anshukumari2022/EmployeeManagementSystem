package com.demo.employeemanagement.dto;

import com.demo.employeemanagement.entity.EmployeeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @Id
    private Long id;
    @NotBlank(message = "Name can't be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should contain only alphabets")
    private String name;

    @NotNull(message = "Aadhaar can't be null")
    @Column(unique = true, name = "aadhaar")
    private Long aadhaar;
    private Integer age;
    private String department;
    private String city;

    @PastOrPresent(message = "Date should not be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    public static EmployeeEntity toEntity(EmployeeDTO employee) {
        return new EmployeeEntity(
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
