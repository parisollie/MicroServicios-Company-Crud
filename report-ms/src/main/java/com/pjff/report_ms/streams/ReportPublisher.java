package com.pjff.report_ms.streams;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {

    // Vid 81
    private final StreamBridge streamBridge;

    /*
     * topic name -> consumerReport
     */
    // Se encarga de recibir el reporte
    public void publishReport(String report) {
        this.streamBridge.send("consumerReport", report);
        this.streamBridge.send("consumerReport-in-0", report);
        this.streamBridge.send("consumerReport-out-0", report);
    }
}
