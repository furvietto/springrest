package com.azienda.esercizioSpringRestBoot.bin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.azienda.esercizioSpringRestBoot.rest","com.azienda.esercizioSpringRestBoot.service"})
@EnableJpaRepositories(basePackages = "com.azienda.esercizioSpringRestBoot.repository")
@EntityScan(basePackages = "com.azienda.esercizioSpringRestBoot.model")
public class EsercizioSpringRestBootApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(EsercizioSpringRestBootApplication.class, args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
