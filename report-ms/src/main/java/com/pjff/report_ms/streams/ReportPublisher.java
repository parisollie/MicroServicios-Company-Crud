package com.pjff.report_ms.streams;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {

    // V-81,paso 20.0
    private final StreamBridge streamBridge;

    /*
    Nuestros topicos se llamaran:
     * topic name -> consumerReport
     */
    // Se encarga de publicar el reporte
    public void publishReport(String report) {
        //Mnamos los topicos, le mandamos el reporte y el nombre
        this.streamBridge.send("consumerReport", report);
        //esto es importante, se crean 3 topicos.
        this.streamBridge.send("consumerReport-in-0", report);
        this.streamBridge.send("consumerReport-out-0", report);
    }
}
