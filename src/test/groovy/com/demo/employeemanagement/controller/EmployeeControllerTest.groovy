package com.demo.employeemanagement.controller

import com.demo.employeemanagement.dto.EmployeeDTO
import com.demo.employeemanagement.service.EmployeeService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.mockito.Mockito.when

@WebMvcTest(EmployeeController)
@AutoConfigureMockMvc
@ContextConfiguration
class EmployeeControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @MockBean
    EmployeeService employeeService;

    def 'should create employee'() {
        given:
        def employeeJSON = """
            {
                "name": "nishant",
                "aadhaar":"1232111",
                "age":"20",
                "department":"CSE",
                "city":"delhi",
                "dob":"01-01-2020"
            }
        """
        when:
        def result = mvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .content(employeeJSON)
                .contentType(MediaType.APPLICATION_JSON));

        then:
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    def "should get employee by Id"() {
        given:
        def id = 1
        def employee = new EmployeeDTO();
        when(employeeService.getEmployeeById(1)).thenReturn(employee)

        when:
        def result = mvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id))

        then:
        def json = result.andReturn().getResponse().getContentAsString();
        def actualEmployee = new ObjectMapper().readValue(json, EmployeeDTO.class);
        actualEmployee == employee;

        and:
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "should get employee by Aadhaar"() {
        given:
        def aadhaar = 123L

        when:
        def result = mvc.perform(MockMvcRequestBuilders.get("/employees/aadhaar/{aadhaar}", aadhaar))

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "should update employee details"() {
        given:
        def aadhaar = 123L
        def employeeJSON = """
            {
                "name": "JohnDoe",
                "aadhaar": ${aadhaar},
                "age": 30,
                "department": "HR",
                "city": "New York",
                "dob": "01-01-1990"
            }
        """

        when:
        def result = mvc.perform(MockMvcRequestBuilders
                .put("/employees/aadhaar/{aadhaar}", aadhaar)
                .content(employeeJSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        then:
        result.andExpect(MockMvcResultMatchers.status().isOk())
    }
}
