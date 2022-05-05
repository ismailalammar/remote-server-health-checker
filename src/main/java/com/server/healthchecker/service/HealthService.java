package com.server.healthchecker.service;

import com.server.healthchecker.service.model.HostProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class HealthService {

    private final HostService hostService;
    private final EmailService emailService;
    private final HostProperties hostProperties;
    private final String email;

    public HealthService(HostService hostService, EmailService emailService , HostProperties hostProperties , @Value("${email.sender.username}") String email) {
        this.hostService = hostService;
        this.emailService = emailService;
        this.hostProperties = hostProperties;
        this.email = email;
    }

    public void check() throws InterruptedException {
        int errorCount = 0;
        // we need to continually ping the remote server
        while (true) {
            boolean serverAlive = hostService.pingURL(hostProperties.getUrl() , hostProperties.getTimeout());
            System.out.println(hostProperties.getUrl() + " is alive : " + serverAlive);
            errorCount = !serverAlive ? errorCount + 1 : 0;
            if(errorCount >= hostProperties.getErrorCount()){
                emailService.sendSimpleMessage(email , hostProperties.getUrl() + " SERVER DOWN" , "we observed that " + hostProperties.getUrl() + "was down on : " + ZonedDateTime.now());
				/*
				 sleep for 30 min after send the notification to give buffer to the tech support to solve the issue ,
				 and we won't exceed the maximum daily pool for email
				 */
                TimeUnit.MINUTES.sleep(30);
                /*
                  reset errorCount to start checking from the beginning
                 */
                errorCount = 0;
            }
            /* 
              sleep for 1 second so it won't add more load to the server
             */
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
