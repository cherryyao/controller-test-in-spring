package com.db.example.db.one.to.n.dto;

import com.db.example.db.one.to.n.entities.Employee;

public class EmployeeDTO {
    private final Long id;
    private final String name;
    private final String gender;
    private final Long companyId;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.gender = employee.getGender();
        this.companyId = employee.getCompany()==null?0:employee.getCompany().getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getGender() {
        return gender;
    }
}
