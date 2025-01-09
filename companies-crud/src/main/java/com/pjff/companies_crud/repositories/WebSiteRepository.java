package com.pjff.companies_crud.repositories;

//Vid 17
import com.pjff.companies_crud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSiteRepository extends JpaRepository<WebSite, Long> {
}

