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
    // Vid 50
    private ReportService reportService;

    @GetMapping(path = "{name}")
    public ResponseEntity<Map<String, String>> getReport(@PathVariable String name) {
        var response = Map.of("report", this.reportService.makeReport(name));
        return ResponseEntity.ok(response);
    }

    // Vid 54
    @PostMapping()
    public ResponseEntity<String> postReport(@RequestBody String report) {
        // Vid 54
        // var response = this.reportService.saveReport(report);
        // return ResponseEntity.ok("OK");
        // Vid 57
        return ResponseEntity.ok(this.reportService.saveReport(report));

    }

    // Vid 58
    @DeleteMapping(path = "{name}")
    public ResponseEntity<Void> deleteReport(@PathVariable String name) {
        this.reportService.deleteReport(name);
        return ResponseEntity.noContent().build();
    }

}