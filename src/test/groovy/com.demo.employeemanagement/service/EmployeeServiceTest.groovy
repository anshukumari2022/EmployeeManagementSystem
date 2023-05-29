package com.demo.employeemanagement.service

import com.demo.employeemanagement.dto.EmployeeDTO
import com.demo.employeemanagement.entity.EmployeeEntity
import com.demo.employeemanagement.exception.ApplicationException
import com.demo.employeemanagement.exception.EmployeeNotFoundException
import com.demo.employeemanagement.repository.EmployeeRepository
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.time.LocalDate
import java.time.Period

class EmployeeServiceTest extends Specification {

    def employeeEntity = new EmployeeEntity(1L, "Anshu", 8208L, 21, "CSE", "Delhi", LocalDate.now())
    def employeeDTO = new EmployeeDTO(1L, "Anshu", 8208L, 21, "CSE", "Delhi", LocalDate.now());

    def employeeRepository = Mock(EmployeeRepository);
    def service = new EmployeeService(employeeRepository);

    def 'should create employee when aadhaar id does not exist'() {
        given:
        employeeRepository.findByAadhaar(_) >> Optional.empty()

        when:
        def result = service.createEmployee(employeeDTO);

        then:
        result == "Employee created with id 1"

        and:
        1 * employeeRepository.save(_) >> employeeEntity;
    }

    def "should throw ApplicationException when employee already exists"() {
        given:
        employeeRepository.findByAadhaar(_) >> Optional.of(employeeEntity)

        when:
        service.createEmployee(employeeDTO)

        then:
        thrown(ApplicationException)
    }

    def "should get employee by Id"() {
        given:
        def employeeId = 1
        employeeRepository.findById(_) >> Optional.of(employeeEntity);

        when:
        def result = service.getEmployeeById(employeeId);

        then:
        result.id == employeeId;
    }

    def "should throw EmployeeNotFoundException when employee does not exist"() {
        given:
        def employeeId = 1
        employeeRepository.findById(_) >> Optional.empty()

        when:
        service.getEmployeeById(employeeId)

        then:
        thrown(EmployeeNotFoundException)
    }

    def "should get employee by aadhaar"() {
        given:
        def aadhaar = 8208L
        employeeRepository.findByAadhaar(_) >> Optional.of(employeeEntity)

        when:
        def result = service.getEmployeeByAadhaar(aadhaar)

        then:
        result.aadhaar == aadhaar
    }

    def "should throw EmployeeNotFoundException when employee with aadhaar does not exist"() {
        given:
        def aadhaar = 8208L
        employeeRepository.findByAadhaar(_) >> Optional.empty()

        when:
        service.getEmployeeByAadhaar(aadhaar)

        then:
        thrown(EmployeeNotFoundException)
    }

    def "should update employee details"() {
        given:
        def aadhaar = 8208L
        def department = "IT"
        employeeRepository.findByAadhaar(_) >> Optional.of(employeeEntity)

        when:
        def result = service.updateEmployeeDetails(aadhaar, department)

        then:
        result.status == HttpStatus.OK

        and:
        employeeEntity.department == "IT"


    }

    def "should throw EmployeeNotFoundException when updating details for non-existing employee"() {
        given:
        def aadhaar = 8208L
        def department = "ECE"
        employeeRepository.findByAadhaar(_) >> Optional.empty()

        when:
        service.updateEmployeeDetails(aadhaar, department)

        then:
        thrown(EmployeeNotFoundException)
    }

    def "should calculate age from date of birth"() {
        given:
        def dob = LocalDate.of(1990, 1, 1)
        def expectedAge = Period.between(dob, LocalDate.now()).getYears()

        when:
        def result = service.calculateAge(dob)

        then:
        result == expectedAge
    }
}