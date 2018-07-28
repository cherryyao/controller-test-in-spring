package com.db.example.db.one.to.n.repositories;

import com.db.example.db.one.to.n.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    List<Employee> findByGender(String gender);

    Page<Employee> findAll(Pageable pageable);
}
