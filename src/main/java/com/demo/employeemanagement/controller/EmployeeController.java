package com.demo.employeemanagement.controller;

import com.demo.employeemanagement.dto.EmployeeDTO;
import com.demo.employeemanagement.exception.ApplicationException;
import com.demo.employeemanagement.exception.EmployeeNotFoundException;
import com.demo.employeemanagement.service.EmployeeService;
import com.demo.employeemanagement.utility.ResponseData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDTO employee) throws ApplicationException {
        String message = employeeService.createEmployee(employee);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/employees/aadhaar/{aadhaar}")
    public EmployeeDTO getEmployeeByAadhaar(@PathVariable("aadhaar") Long aadhaar) throws EmployeeNotFoundException {
        return employeeService.getEmployeeByAadhaar(aadhaar);
    }

    @PutMapping("/employees/aadhaar/{aadhaar}")
    public ResponseEntity<ResponseData> updateEmployeeDetails(@PathVariable("aadhaar") Long aadhaar, @Valid @RequestBody EmployeeDTO employee) throws EmployeeNotFoundException {
        return employeeService.updateEmployeeDetails(aadhaar, employee.getDepartment());
    }
}
