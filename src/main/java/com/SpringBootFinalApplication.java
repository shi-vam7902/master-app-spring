package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFinalApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getEncoder() {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		System.out.println("Endcoding all passwords  with BCryptEncoder");
		return bcrypt;
	}
}
