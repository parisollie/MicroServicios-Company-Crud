package com.pjff.report_ms.services;

import com.pjff.report_ms.helpers.ReportHelper;
import com.pjff.report_ms.models.Company;
import com.pjff.report_ms.models.WebSite;
import com.pjff.report_ms.repositories.CompaniesFallbackRepository;
import com.pjff.report_ms.repositories.CompaniesRepository;
import com.pjff.report_ms.streams.ReportPublisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
// V-48,paso 54, implementamos de nuestra intace.
public class ReportServiceImpl implements ReportService {
    // Paso 55,inyectamos
    private final CompaniesRepository companiesRepository;
    // V-52,Paso 60, inyectamos el helper
    private final ReportHelper reportHelper;
    // V-71
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    // V-81
    private final ReportPublisher reportPublisher;

    @Override
    public String makeReport(String name) {
        // V-71
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallback(name, throwable));
        // V-53,Paso 61 le pasamos el reporte
        // return
        // reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
        // V-50
        // return this.companiesRepository.getByName(name).orElseThrow().getName();
    }

    @Override
    public String saveReport(String report) {
        // V-56,paso 67,para cortar lo que queremos llevarnos
        //{Uber} was founded in {14/08/2012} by {Some Owner} the websites from this company are {uber, uber eats}
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelper.getPlaceholdersFromTemplate(report);
        //Obtenemos todos los websites, quiero la posicion 3
        var webSites = Stream.of(placeholders.get(3))
                .map(website -> WebSite.builder().name(website).build())
                //Lo transfromamos en una lista finalmente
                .toList();

        // V-54 y v-56, Paso 68
        var company = Company.builder()
                .name(placeholders.get(0))
                //Lo trasformamos en un objeto local date
                .foundationDate(LocalDate.parse(placeholders.get(1), format))
                .founder(placeholders.get(2))
                .webSites(webSites)
                .build();

        // Vid 81, add el publisher
        this.reportPublisher.publishReport(report);
        this.companiesRepository.postByName(company);
        return "Saved";
    }

    // V-58,paso 72
    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deleteByName(name);
    }

    // Vid 71
    private String makeReportMain(String name) {
        return reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }

    private String makeReportFallback(String name, Throwable error) {
        log.warn(error.getMessage());
        return reportHelper.readTemplate(this.companiesFallbackRepository.getByName(name));
    }
}
