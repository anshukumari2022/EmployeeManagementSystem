package com.demo.employeemanagement.entity


import spock.lang.Specification

import java.time.LocalDate

class EmployeeEntityTest extends Specification {
    def "should convert entity to dto"() {
        given:
        def employeeEntity = new EmployeeEntity(1L, "Anshu", 3030L, 20, "CSE", "Delhi", LocalDate.now());

        when:
        def dto = EmployeeEntity.toDTO(employeeEntity);

        then:
        dto.id == employeeEntity.id;
        dto.name == employeeEntity.name
        dto.aadhaar == employeeEntity.aadhaar
        dto.age == employeeEntity.age
        dto.department == employeeEntity.department
        dto.city == employeeEntity.city
        dto.dob == employeeEntity.dob
    }
}
