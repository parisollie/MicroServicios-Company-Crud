package com.pjff.companies_crud.repositories;

import com.pjff.companies_crud.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Vid 17
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByName(String name);
}

