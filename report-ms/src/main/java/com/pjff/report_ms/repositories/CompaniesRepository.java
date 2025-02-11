package com.pjff.report_ms.repositories;

import com.pjff.report_ms.beans.LoadBalancerConfiguration;
import com.pjff.report_ms.models.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//V-47,Paso 49, creamos la interfaz y le ponemos el Feing
@FeignClient(name = "companies-crud")
//Paso 51, configuramos el balanceo de cargas
@LoadBalancerClient(name = "companies-crud", configuration = LoadBalancerConfiguration.class)
public interface CompaniesRepository {

    @GetMapping(path = "/companies-crud/company/{name}")
    //Paso 52
    Optional<Company> getByName(@PathVariable String name);

    // V-54,hacemos el post paso 62
    @PostMapping(path = "/companies-crud/company")
    //Tambien regresa un optional
    Optional<Company> postByName(@RequestBody Company company);

    // V-58,paso 70
    @DeleteMapping(path = "/companies-crud/company/{name}")
    void deleteByName(@PathVariable String name);
}
