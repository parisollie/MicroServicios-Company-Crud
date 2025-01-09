package com.pjff.companies_crud.services;

import com.pjff.companies_crud.entities.Company;

//Vid 16
public interface CompanyService {
    //Creamos el CRUD
    Company create(Company company);
    Company readByName(String name);
    Company update(Company company, String name);
    void  delete(String name);
}

