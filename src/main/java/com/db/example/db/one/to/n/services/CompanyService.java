package com.db.example.db.one.to.n.services;

import com.db.example.db.one.to.n.dto.CompanyDTO;
import com.db.example.db.one.to.n.dto.EmployeeDTO;
import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import com.db.example.db.one.to.n.repositories.CompanyRepository;
import com.db.example.db.one.to.n.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public boolean createCompany(Company company) {
        company.getEmployeeList().stream()
                .forEach(employee -> employee.setCompany(company));
        companyRepository.save(company);
        return true;
    }


    public List<CompanyDTO> getAllCompany() {
        return companyRepository.findAll().stream()
                .map(company -> new CompanyDTO(company))
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        return new CompanyDTO(companyRepository.findById(id).get());
    }

    public List<CompanyDTO> getCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAll(PageRequest.of(page,pageSize)).stream()
                .map(company -> new CompanyDTO(company))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesFromCompany(long id) {
        return companyRepository.findById(id).get().getEmployeeList().stream()
                .map(employee -> new EmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    public boolean updateCompany( Company company) {
        company.getEmployeeList().stream().filter(employee -> employee.getCompany() == null).forEach(employee -> {
            employee.setCompany(company);
        });
        long id = company.getId();
        companyRepository.changeNameById(id,company.getName());
        companyRepository.save(company);
        return true;
    }

    public Company deleteCompany(long id) {
        Company one = companyRepository.findById(id).get();
        companyRepository.deleteById(id);
        return one;
    }


}
