package com.pjff.report_ms.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//Vid 69
@Configuration
public class WebClientBean {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
