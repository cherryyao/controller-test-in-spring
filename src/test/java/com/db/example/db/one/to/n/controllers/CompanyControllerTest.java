package com.db.example.db.one.to.n.controllers;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {
    @Autowired
    CompanyController companyController;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void should_create_new_Company_when_given_company() throws Exception{
        //given
        Company company = new Company("oocl");
        when(companyService.createCompany(any(Company.class))).thenReturn(true);
        //when
        ResultActions result = mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company)));
        //then
        result.andExpect(status().isCreated()).andDo(print());

    }

    @Test
    public void should_get_all_Company()throws Exception {
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"abc");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        CompanyDTO companyDTO2 = new CompanyDTO(company2);
        List<CompanyDTO> companyList = Arrays.asList(companyDTO1,companyDTO2);
        given(companyService.getAllCompany()).willReturn(companyList);
        //when
        ResultActions result = mvc.perform(get("/companies"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",containsString("oocl")))
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].name",containsString("abc")));
    }

    @Test
    public void should_get_Company_By_Id() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        System.out.println(company1.getId());
        CompanyDTO companyDTO = new CompanyDTO(company1);
        System.out.println(companyDTO.getId());
        when(companyService.getCompanyById(any())).thenReturn(companyDTO);
        //when
        ResultActions result = mvc.perform(get("/companies/{1}",1L));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(companyDTO.getId().intValue())))
                .andExpect(jsonPath("$.name",containsString("oocl")))
        .andDo(print());
    }

    @Test
    public void should_get_Companies_By_Page() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"abc");
        Company company3 = new Company(3L,"ddd");
        CompanyDTO companyDTO1 = new CompanyDTO(company1);
        CompanyDTO companyDTO2 = new CompanyDTO(company2);
        List<CompanyDTO> companyList = Arrays.asList(companyDTO1,companyDTO2);
        when(companyService.getCompaniesByPage(anyInt(),anyInt())).thenReturn(companyList);
        //when
        ResultActions result = mvc.perform(get("/companies/page/1/pageSize/2"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].name",containsString("oocl")))
                .andExpect(jsonPath("$[1].id",is(2)))
                .andExpect(jsonPath("$[1].name",containsString("abc")));
    }

//    @Test
//    public void shoule_get_Employees_From_Company_when_given_companyId() throws Exception{
//        //given
//        Employee employee1 = new Employee("jack","male");
//        Employee employee2 = new Employee("tom","male");
//        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1);
//        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2);
//        List<EmployeeDTO> employeeDTOList = Arrays.asList(employeeDTO1,employeeDTO2);
//        when(companyService.getEmployeesFromCompany(anyInt())).thenReturn(employeeDTOList);
//        //when
//        ResultActions result = mvc.perform(get("/companies/1/employees"));
//        //then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(2)))
//                .andExpect(jsonPath("$[0].id",is(1)))
//                .andExpect(jsonPath("$[0].name",containsString("jack")))
//                .andExpect(jsonPath("$[0].gender",is("male")))
//                .andExpect(jsonPath("$[1].id",is(2)))
//                .andExpect(jsonPath("$[1].name",containsString("tom")))
//                .andExpect(jsonPath("$[1].gender",is("male")));
//    }

    @Test
    public void should_return_status_when_update_Company() throws Exception{
        //given
        Company company = new Company(1L,"oocl");
        when(companyService.updateCompany(any())).thenReturn(true);
        //when
        ResultActions result = mvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company)));
        //then
        result.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void should_delete_Company_when_given_companyID() throws Exception{
        //given
        Company company1 = new Company(1L,"oocl");
        Company company2 = new Company(2L,"aaa");
        given(companyService.deleteCompany(anyLong())).willReturn(company1);
        //when(companyController.deleteCompany(any())).thenReturn(company1);

        //when
        ResultActions result = mvc.perform(delete("/companies/{id}",1L));
        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",containsString("oocl")));

    }
}