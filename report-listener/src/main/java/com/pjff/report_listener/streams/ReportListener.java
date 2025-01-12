package com.pjff.report_listener.streams;

import com.pjff.report_listener.documents.ReportDocument;
import com.pjff.report_listener.repositories.ReportRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

//Vid 84
@Configuration
@Slf4j
@AllArgsConstructor
public class ReportListener {

    //Vid 86
    private final ReportRepository reportRepository;

    @Bean
    public Consumer<String> consumerReport() {
        return report -> {
            this.reportRepository.save(ReportDocument.builder().content(report).build());
            log.info("Saving report: {}", report);
        };
    }
}
