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

@Service
@Transactional
// Trabajar con logs
@Slf4j
// Para que nos cree un constructor con todos los argumentos.
@AllArgsConstructor
// Vid 16
public class CompanyServiceImpl implements CompanyService {

    // Vid 18
    private final CompanyRepository companyRepository;
    // Vid 96
    private final Tracer tracer;

    @Override
    public Company create(Company company) {
        // Vid 18, recorremos las compañias y sino tiene categoria le ponemos NONE
        company.getWebSites().forEach(webSite -> {
            // Si es nulo,
            if (Objects.isNull(webSite.getCategory())) {
                webSite.setCategory(Category.NONE);
            }
        });
        return this.companyRepository.save(company);
    }

    @Override
    public Company readByName(String name) {
        // Vid 96
        var spam = tracer.nextSpan().name("readByName");
        try (Tracer.SpanInScope spanInScope = this.tracer.withSpan(spam.start())) {
            log.info("Getting comany from DB");
        } finally {
            spam.end();
        }
        // Vid 18
        return this.companyRepository.findByName(name)
                // En caso de que no encuentres nada ,lanza:
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        // Vid 18, evaluamos que exista la compañia.
        var companyToUpdate = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
        companyToUpdate.setLogo(company.getLogo());
        companyToUpdate.setFoundationDate(company.getFoundationDate());
        companyToUpdate.setFounder(company.getFounder());
        return this.companyRepository.save(companyToUpdate);
    }

    @Override
    public void delete(String name) {
        var companyToDelete = this.companyRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));
        this.companyRepository.delete(companyToDelete);
    }
}
