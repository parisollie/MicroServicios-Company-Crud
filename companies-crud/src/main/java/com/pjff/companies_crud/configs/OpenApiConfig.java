package com.pjff.companies_crud.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

//V-22,paso 28, configuracion para Swagger
@Configuration
@OpenAPIDefinition(info = @Info(title = "companies CRUD", version = "1.0.0", description = "This is a CRUD for management companies"))
public class OpenApiConfig {
}
