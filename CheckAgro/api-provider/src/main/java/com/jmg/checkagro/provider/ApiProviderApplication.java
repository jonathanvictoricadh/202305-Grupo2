package com.jmg.checkagro.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApiProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProviderApplication.class, args);
	}

}
