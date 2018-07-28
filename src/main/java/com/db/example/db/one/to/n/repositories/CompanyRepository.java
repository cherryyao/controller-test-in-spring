package com.db.example.db.one.to.n.repositories;

import com.db.example.db.one.to.n.entities.Company;
import com.db.example.db.one.to.n.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    List<Company> findAll();

    Optional<Company> findById(Long id);

    Page<Company> findAll(Pageable pageable);

    Company save(Company company);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Company company SET company.name = :name WHERE company.id = :id")
    @Transactional
    int changeNameById(@Param("id") Long id, @Param("name") String name);

    int deleteCompanyById(@Param("id") Long id);
}
