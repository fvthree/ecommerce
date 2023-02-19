package com.fvthree.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("ecommerce.app")
@Data
public class CustomPropertiesConfig {
	
	private String jwtSecret;

    private int jwtExpirationMs;
    
    private int jwtRefreshExpirationMs;

}
