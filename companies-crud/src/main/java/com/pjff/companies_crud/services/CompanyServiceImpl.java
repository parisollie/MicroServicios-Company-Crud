package com.pjff.companies_crud.services;

import com.pjff.companies_crud.entities.Category;
import com.pjff.companies_crud.entities.Company;
import io.micrometer.tracing.Tracer;
import com.pjff.companies_crud.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

//V-18,paso 15, empezamos a trabajar con este archivo.
@Service
// Clase para manejar la transeccional de spring
@Transactional
// Trabajar con logs
@Slf4j
// Para que nos cree un constructor con todos los argumentos.
@AllArgsConstructor
// V-16
public class CompanyServiceImpl implements CompanyService {

    // Paso 16, inyectamos el repositorio.
    private final CompanyRepository companyRepository;
    // V-96,paso 3.10
    private final Tracer tracer;

    @Override
    public Company create(Company company) {
        /* Paso 18, recorremos las compañias y sino tiene categoria
        le ponemos NONE por defecto */
        company.getWebSites().forEach(webSite -> {
            // Si es nulo,le ponemos la cafegoria por default None
            if (Objects.isNull(webSite.getCategory())) {
                webSite.setCategory(Category.NONE);
            }
        });
        return this.companyRepository.save(company);
    }

    @Override
    public Company readByName(String name) {
        // V-96,paso 3.11
        var spam = tracer.nextSpan().name("readByName");
        // Para cerrar un buffer que esta abierto
        try (Tracer.SpanInScope spanInScope = this.tracer.withSpan(spam.start())) {
            log.info("Getting comany from DB");
        } finally {
            spam.end();
        }
        //Paso 17
        return this.companyRepository.findByName(name)
                // En caso de que no encuentres nada ,lanza la siguiente excepcion:
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        // Paso 19, evaluamos que exista la compañia.
        var companyToUpdate = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
        companyToUpdate.setLogo(company.getLogo());
        companyToUpdate.setFoundationDate(company.getFoundationDate());
        companyToUpdate.setFounder(company.getFounder());
        //finalmente salvamos
        return this.companyRepository.save(companyToUpdate);
    }

    @Override
    //paso 20
    public void delete(String name) {
        var companyToDelete = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
        this.companyRepository.delete(companyToDelete);
    }
}
