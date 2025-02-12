package com.pjff.companies_crud.repositories;


import com.pjff.companies_crud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

//V-17,paso 13 hacemos el repositorio de web site.
public interface WebSiteRepository extends JpaRepository<WebSite, Long> {
}

