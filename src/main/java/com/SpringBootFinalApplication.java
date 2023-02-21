package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

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
	@Bean
	public OpenAPI customeApi(@Value("place-microservice") String AppDesc,@Value("1.0") String AppVersion) {
		return new OpenAPI()
				.info(new Info()
						.title("places-microservice")
						.version(AppVersion)
						.description(AppDesc)
						.termsOfService("https://app.termly.io/dashboard/website/new")
						.license(new License().name("Apache 2.0").url("https://springdoc.org/")));
	}
}
