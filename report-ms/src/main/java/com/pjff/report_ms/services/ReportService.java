package com.pjff.report_ms.services;

public interface ReportService {

    //V-48,Paso 53 propiedades el reporte
    String makeReport(String name);
    String saveReport(String report);
    void deleteReport(String name);
}

