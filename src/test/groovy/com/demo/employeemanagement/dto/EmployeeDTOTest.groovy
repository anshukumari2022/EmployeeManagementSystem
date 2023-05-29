package com.demo.employeemanagement.dto


import spock.lang.Specification

import java.time.LocalDate

class EmployeeDTOTest extends Specification {
    def "should convert dto to entity"() {
        given:
        def employeeDTO = new EmployeeDTO(1L, "Anshu", 3030L, 20, "CSE", "Delhi", LocalDate.now());

        when:
        def entity = EmployeeDTO.toEntity(employeeDTO);

        then:
        entity.id == employeeDTO.id;
        entity.name == employeeDTO.name
        entity.aadhaar == employeeDTO.aadhaar
        entity.age == employeeDTO.age
        entity.department == employeeDTO.department
        entity.city == employeeDTO.city
        entity.dob == employeeDTO.dob
    }
}
