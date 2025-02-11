package com.pjff.report_ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// V-46,Paso 41 ,lo ponemos para configurarlo con el Eureka
@EnableDiscoveryClient
// V-48,paso 53, ponemos esto para que no nos cause error
@EnableFeignClients
public class ReportMsApplication {

	// V-49,Paso 56 para hacer pruebas
	@Autowired
	// private EurekaClient eurekaClient;

	public static void main(String[] args) {
		SpringApplication.run(ReportMsApplication.class, args);
	}

	/*
	 * @Override
	 * public void run(String... args) throws Exception {
	 * this.eurekaClient.getAllKnownRegions().forEach(System.out::println);
	 * System.out.println(this.eurekaClient.getApplication("companies-crud"));
	 * }
	 */
}
