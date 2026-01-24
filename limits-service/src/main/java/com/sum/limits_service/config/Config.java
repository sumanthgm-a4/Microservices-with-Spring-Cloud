package com.sum.limits_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "limits-service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {
    private int minimum;
    private int maximum;
}
