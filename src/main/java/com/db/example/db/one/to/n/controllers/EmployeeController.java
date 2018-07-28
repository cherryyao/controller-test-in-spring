package com.db.example.db.one.to.n.controllers;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Transactional
    @PostMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCompany(@RequestBody Employee employee){
        if (employeeService.createEmployee(employee)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Transactional
    @GetMapping(path = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @Transactional
    @GetMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);
    }

    @Transactional
    @GetMapping(path = "/male",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getMaleEmployee(){
        return employeeService.getMaleEmployee();
    }

    @Transactional
    @GetMapping(path = "/page/{page}/size/{size}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getEmployeesByPage(@PathVariable int page,@PathVariable int size){
        return employeeService.getEmployeesByPage(page,size);
    }
}
