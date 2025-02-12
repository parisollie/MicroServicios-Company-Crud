package com.pjff.companies_crud.controllers;

import com.pjff.companies_crud.entities.Company;
import com.pjff.companies_crud.services.CompanyService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
// Como accedere al controlador
@RequestMapping(path = "company")
@Slf4j
// V-22,add el tag,paso 28, es para Swagger
@Tag(name = "Companies resource")
// V-19,paso 21, creamos el controlador
public class CompanyController {
    //Paso 22,Inyectamos
    private final CompanyService companyService;

    // Paso 29, ponemos el operation para el Swagger
    @Operation(summary = "get a company given a company name")
    //El mapping con el que accederemos
    @GetMapping(path = "{name}")
    // V-95,paso 3.9
    @Observed(name = "company.name")
    @Timed(value = "company.name")
    //Paso 23
    public ResponseEntity<Company> get(@PathVariable String name) {

        /*
         * Vid 71
         * try {
         * Thread.sleep(5000);
         * } catch (InterruptedException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         */

        // El log para la compañia
        log.info("GET: company {}", name);
        // el body viene del servicio.
        return ResponseEntity.ok(this.companyService.readByName(name));
    }

    @Operation(summary = "save in DB a company given a company from body")
    @PostMapping
    // V-95
    @Observed(name = "company.save")
    @Timed(value = "company.save")
    //Paso 24, creamos el post, le pasamos el body como un Json
    public ResponseEntity<Company> post(@RequestBody Company company) {
        log.info("Post: company {}", company.getName());
        return ResponseEntity.created(
                //Llamo a  mi servicio obtengo el nombre y se lo pongo.
                URI.create(this.companyService.create(company).getName()))
                .build();
    }

    @Operation(summary = "update in DB a company given a company from body")
    @PutMapping(path = "{name}")
    //Paso 26, este es parecido al get, solo lo pegamos y ya
    public ResponseEntity<Company> put(@RequestBody Company company,
            @PathVariable String name) {
        log.info("PUT: company {}", name);
        return ResponseEntity.ok(this.companyService.update(company, name));
    }

    @Operation(summary = "delete in DB a company given a company name")
    @DeleteMapping(path = "{name}")
    //Paso 27, el delete nos regresa void (?) regreso defirentes tipo de objeto
    public ResponseEntity<?> delete(@PathVariable String name) {
        log.info("DELETE: company {}", name);
        this.companyService.delete(name);
        /*Hacemos la llamada a nuestro servicio , no content 204
         en el metodo no content y  create es necesario poner el build.*/
        return ResponseEntity.noContent().build();
    }
}
