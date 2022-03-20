package com.server.healthchecker;

import com.server.healthchecker.service.EmailService;
import com.server.healthchecker.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class HealthCheckerApplication implements CommandLineRunner {

	@Autowired
	HostService hostService;

	@Autowired
	EmailService emailService;

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

			if(!serverAlive)
				errorCount += 1;
			else
				errorCount = 0; // we only want to send email if we got 5 consecutive errors

			if(errorCount >= 5){
				emailService.sendSimpleMessage("person_email_to_get_action" , host + " SERVER DOWN" , "we observed that " + host + "was down on : " + ZonedDateTime.now());

				/*
				 sleep for 30 min after send the notification to give buffer to the tech support to solve the issue , and we won't exceed the maximum daily pool for email
				 */
				TimeUnit.MINUTES.sleep(30);
				/**
				 * reset errorCount to start checking from the beginning
				 */
				
				errorCount = 0;
			}

			// sleep for 1 second so it won't add more load to the server
			TimeUnit.SECONDS.sleep(2);
		}
	}
}
