package com.db.example.db.one.to.n.controllers;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.services.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void createCompany() throws Exception{
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
    public void getCompanyById() throws Exception{
    }

    @Test
    public void getCompaniesByPage() throws Exception{
    }

    @Test
    public void getEmployeesFromCompany() throws Exception{
    }

    @Test
    public void updateCompany() throws Exception{
    }

    @Test
    public void deleteCompany() throws Exception{
    }
}