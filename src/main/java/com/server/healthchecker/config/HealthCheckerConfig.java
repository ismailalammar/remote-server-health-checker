package com.server.healthchecker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:healthChecker.properties"), //common properties
        @PropertySource("classpath:healthChecker-${spring.profiles.active}.properties")
})
public class HealthCheckerConfig {
}
