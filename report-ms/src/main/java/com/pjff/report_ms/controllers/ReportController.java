package com.pjff.report_ms.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pjff.report_ms.services.ReportService;

import java.util.Map;

@RestController
@RequestMapping(path = "report")
@AllArgsConstructor
public class ReportController {
    // V-50,Paso 57, creamos el controlador  e inyectamos el report service
    private ReportService reportService;

    @GetMapping(path = "{name}")
    //Creamos un mapa con una llave
    public ResponseEntity<Map<String, String>> getReport(@PathVariable String name) {
        //creamos un mapa con llave valor
        var response = Map.of("report", this.reportService.makeReport(name));
        return ResponseEntity.ok(response);
    }

    // V-54
    @PostMapping()
    public ResponseEntity<String> postReport(@RequestBody String report) {
        // V-54,paso 64, para salvar el reporte
        // var response = this.reportService.saveReport(report);
        // return ResponseEntity.ok("OK");
        // V-57,paso 69
        return ResponseEntity.ok(this.reportService.saveReport(report));

    }

    // V-58,para eliminar
    @DeleteMapping(path = "{name}")
    public ResponseEntity<Void> deleteReport(@PathVariable String name) {
        this.reportService.deleteReport(name);
        return ResponseEntity.noContent().build();
    }

}