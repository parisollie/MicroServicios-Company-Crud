package com.pjff.report_ms.helpers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pjff.report_ms.models.Company;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//V-52,paso 58
@Component
@Slf4j
public class ReportHelper {

    //Paso 59, esto lo inyectamos directo de Github
    @Value("${report.template}")
    private String reportTemplate;

    public String readTemplate(Company company) {
        //Paso 60, con esto ya generamos el reporte
        return this.reportTemplate
                .replace("{company}", company.getName())
                .replace("{foundation_date}", company.getFoundationDate().toString())
                .replace("{founder}", company.getFounder())
                .replace("{web_sites}", company.getWebSites().toString());
    }

    // V-56,paso 66 para extraer las palabras
    //{Uber} was founded in {14/08/2012} by {Some Owner} the websites from this company are {uber, uber eats}
    public List<String> getPlaceholdersFromTemplate(String template) {
        // Parteme todo lo que empiece  llave
        var split = template.split("\\{");

        // Filtrado por streams
        return Arrays.stream(split)
                // hacemos un filtrado por las lineas que no esten vacias.
                .filter(line -> !line.isEmpty())
                // Hacemos un transformado
                .map(line -> {
                    // Hacemos una variable llamada index
                    var index = line.indexOf("}");
                    // Transformamos todo estoentre el index o y el index
                    return line.substring(0, index);
                })
                // transformamos en una lista de strings
                .collect(Collectors.toList());

    }
}
