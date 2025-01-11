package com.pjff.report_ms.services;

import com.pjff.report_ms.helpers.ReportHelper;
import com.pjff.report_ms.models.Company;
import com.pjff.report_ms.models.WebSite;
import com.pjff.report_ms.repositories.CompaniesFallbackRepository;
import com.pjff.report_ms.repositories.CompaniesRepository;
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
// Vid 48
public class ReportServiceImpl implements ReportService {
    // Vid 48
    private final CompaniesRepository companiesRepository;
    // Vid 52
    private final ReportHelper reportHelper;
    // Vid 71
    private final CompaniesFallbackRepository companiesFallbackRepository;

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    /*
     * 
     * private final ReportPublisher reportPublisher;
     */

    @Override
    public String makeReport(String name) {
        // Vid 71
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallback(name, throwable));
        // Vid 53
        // return
        // reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
        // Vid 50
        // return this.companiesRepository.getByName(name).orElseThrow().getName();
    }

    @Override
    public String saveReport(String report) {
        // Vid 56
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholders = this.reportHelper.getPlaceholdersFromTemplate(report);
        var webSites = Stream.of(placeholders.get(3))
                .map(website -> WebSite.builder().name(website).build())
                .toList();

        // Vid 54 y vid 56
        var company = Company.builder()
                .name(placeholders.get(0))
                .foundationDate(LocalDate.parse(placeholders.get(1), format))
                .founder(placeholders.get(2))
                .webSites(webSites)
                .build();

        // this.reportPublisher.publishReport(report);
        this.companiesRepository.postByName(company);
        return "Saved";
    }

    // Vid 58
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
