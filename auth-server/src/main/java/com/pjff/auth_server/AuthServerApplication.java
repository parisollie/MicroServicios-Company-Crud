package com.pjff.auth_server;

import com.sun.jdi.PrimitiveValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthServerApplication {

	/*
	 * Vid 110
	 * 
	 * @Autowired
	 * private BCryptPasswordEncoder encoder;
	 */

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	/*
	 * Vid 110
	 * 
	 * @Override
	 * public void run(String... args) throws Exception {
	 * System.out.println("Password:"+this.encoder.encode("secret"));
	 */

}
