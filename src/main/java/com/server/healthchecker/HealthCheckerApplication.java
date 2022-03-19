package com.server.healthchecker;

import com.server.healthchecker.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealthCheckerApplication implements CommandLineRunner {

	@Autowired
	HostService hostService;

	public static void main(String[] args) {
		SpringApplication.run(HealthCheckerApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException {
		String host = "http://google.com"; // remote server we need to monitor
		int timeout = 3000; // in milliseconds
		short errorCount = 0 ;
		// we need to continually ping the remote server
		while (true) {
			boolean serverAlive = hostService.pingURL(host , timeout);
			System.out.println(host + " is alive : " + serverAlive);
			// sleep for 1 second so it won't add more load to the server
			Thread.sleep(2000);
		}
	}
}
