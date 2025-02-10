package com.pjff.companies_crud.repositories;
//V-17,paso 13
import com.pjff.companies_crud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

//Hacemos el repositorio de web site
public interface WebSiteRepository extends JpaRepository<WebSite, Long> {
}

