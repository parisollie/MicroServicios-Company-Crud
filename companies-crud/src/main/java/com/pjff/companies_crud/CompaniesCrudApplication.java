package com.pjff.companies_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// Vid 89
@EnableDiscoveryClient
public class CompaniesCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompaniesCrudApplication.class, args);
	}

}
