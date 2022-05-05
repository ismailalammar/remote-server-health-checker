package com.server.healthchecker.service.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("host")
public class HostProperties {
    private String url;
    private int timeout;
    private int errorCount;
}
