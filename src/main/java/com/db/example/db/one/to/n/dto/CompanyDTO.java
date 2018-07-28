package com.db.example.db.one.to.n.dto;

import com.db.example.db.one.to.n.entities.Company;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDTO {
    private final Long id;
    private final String name;
    private final ZonedDateTime createDate;
    private final List<EmployeeDTO> employeeList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public List<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.createDate = company.getCreatedDate();
        this.employeeList = company.getEmployeeList().stream().map(employee -> new EmployeeDTO(employee)).collect(Collectors.toList());
    }
}
