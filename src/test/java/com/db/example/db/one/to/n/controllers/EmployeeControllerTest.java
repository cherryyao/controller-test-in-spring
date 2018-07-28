package com.db.example.db.one.to.n.controllers;

import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_create_Company_when_given_new_company() throws Exception{
//given
        Employee employee = new Employee("jack","male");
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(true);
        //when
        ResultActions result = mvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)));
        //then
        result.andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void should_get_All_Employee() throws Exception{
        //given
        Employee employee1 = new Employee(1L,"jack","male");
        Employee employee2 = new Employee(2L,"tom","male");
        EmployeeDTO employeeDTO1= new EmployeeDTO(employee1);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
        List<EmployeeDTO> employeeDTOList = Arrays.asList(employeeDTO1,employeeDTO2);
        given(employeeService.getAllEmployee()).willReturn(employeeDTOList);
        //when
        ResultActions result = mvc.perform(get("/employees"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",containsString("jack")))
                .andExpect(jsonPath("$[0].gender",is("male")))
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].name",containsString("tom")));
    }

    @Test
    public void should_get_Employee_By_Id() throws Exception{
        //given
        Employee employee = new Employee(1L,"jack","male");
        EmployeeDTO employeeDTO = new EmployeeDTO(employee);
        when(employeeService.getEmployeeById(any())).thenReturn(employeeDTO);
        //when
        ResultActions result = mvc.perform(get("/employees/{1}",1L));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employeeDTO.getId().intValue())))
                .andExpect(jsonPath("$.name",containsString("jack")))
                .andExpect(jsonPath("$.gender",containsString("male")))
                .andDo(print());
    }

    @Test
    public void should_get_Male_Employee_when_given_male() throws Exception{
        //given
        Employee employee1 = new Employee(1L,"jack","male");
        Employee employee2 = new Employee(2L,"mary","female");
        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
        List<EmployeeDTO> employeeDTOList = Arrays.asList(employeeDTO1);
        when(employeeService.getMaleEmployee()).thenReturn(employeeDTOList);
        //when
        ResultActions result = mvc.perform(get("/employees/male"));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",is("jack")));
    }

    @Test
    public void getEmployeesByPage()throws Exception {
    }
}