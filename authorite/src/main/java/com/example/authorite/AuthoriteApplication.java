package com.example.authorite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class AuthoriteApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthoriteApplication.class, args);
	}

}
