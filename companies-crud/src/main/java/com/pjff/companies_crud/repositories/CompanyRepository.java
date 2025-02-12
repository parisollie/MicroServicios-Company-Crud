package com.pjff.companies_crud.repositories;

import com.pjff.companies_crud.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//V-17,paso 12, creamos el repository los extendemos , nuestro tipo de id es long
public interface CompanyRepository extends JpaRepository<Company, Long> {
    //Paso 14,para que lo busque por el nombre
    Optional<Company> findByName(String name);
}

