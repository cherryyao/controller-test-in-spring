package com.db.example.db.one.to.n.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String gender;

    @CreatedDate
    private ZonedDateTime createdDate = ZonedDateTime.now();

    public void setCompany(Company company) {
        this.company = company;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;


    public Employee(String name,String gender) {
        this.name = name;
        this.gender = gender;
    }

    public Employee() {
    }

    public Employee(long id,String name,String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public Employee(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }



    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
