package com.pjff.report_ms.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.pjff.report_ms.models.Company;

//Vid 70
@Repository
@Slf4j
public class CompaniesFallbackRepository {

    // Inyectamos
    private final WebClient webClient;
    private final String uri;

    // generamos un constructor a mano,para tener el @valu
    public CompaniesFallbackRepository(WebClient webClient,
            @Value("${fallback.uri}") String uri) {
        this.webClient = webClient;
        this.uri = uri;
    }

    public Company getByName(String name) {
        log.warn("Calling companies fallback {}", uri);

        return this.webClient
                .get()
                .uri(uri, name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Company.class)
                .log()
                .block();
    }
}
