package com.demo.employeemanagement.service;

import com.demo.employeemanagement.dto.EmployeeDTO;
import com.demo.employeemanagement.entity.EmployeeEntity;
import com.demo.employeemanagement.utility.ResponseData;
import com.demo.employeemanagement.exception.ApplicationException;
import com.demo.employeemanagement.exception.EmployeeNotFoundException;
import com.demo.employeemanagement.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static com.demo.employeemanagement.utility.Constants.*;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public String createEmployees(EmployeeDTO employeeDTO) throws ApplicationException {

        Optional<EmployeeEntity> isEmployeeExist = employeeRepository.findByAadhaar(employeeDTO.getAadhaar());
        if (isEmployeeExist.isPresent()) {
            throw new ApplicationException(EMPLOYEE_ALREADY_EXIST);
        }
        employeeDTO.setAge(calculateAge(employeeDTO.getDob()));
        EmployeeEntity employee = employeeRepository.save(EmployeeDTO.toEntity(employeeDTO));

        return EMPLOYEE_CREATED_WITH_ID + " " + employee.getId();
    }

    public EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST));
        return EmployeeEntity.toDTO(employee);
    }

    public EmployeeDTO getEmployeeByAadhaar(Long aadhaar) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findByAadhaar(aadhaar)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST_WITH_AADHAAR + " " + aadhaar));

        return EmployeeEntity.toDTO(employee);
    }

    public ResponseEntity<ResponseData> updateEmployeeDetails(Long aadhaar, String department) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findByAadhaar(aadhaar)
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_EXIST_WITH_AADHAAR + " " + aadhaar));

        employee.setDepartment(department);
        ResponseData response = new ResponseData("Success", "Updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static int calculateAge(LocalDate dob) {
        LocalDate curDate = LocalDate.now();
        if ((dob != null) && (curDate != null))
            return Period.between(dob, curDate).getYears();
        else return 0;
    }
}
