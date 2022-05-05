package com.server.healthchecker;

import com.server.healthchecker.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthCheckerApplication implements CommandLineRunner {

	@Autowired
	HealthService healthService;

	public static void main(String[] args) {
		SpringApplication.run(HealthCheckerApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		healthService.check();
	}
}
