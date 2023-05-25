package com.demo.employeemanagement.repository;

import com.demo.employeemanagement.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByAadhaar(Long id);

}
