package com.hanait.noninvasiveglucosespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NonInvasiveGlucoseSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(NonInvasiveGlucoseSpringApplication.class, args);
	}

}
