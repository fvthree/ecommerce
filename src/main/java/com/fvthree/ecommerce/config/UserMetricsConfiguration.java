package com.fvthree.ecommerce.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class UserMetricsConfiguration {
	
	@Bean
    public Counter createdUserCreationCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.created")
                .description("Number of User Created")
                .tags("environment", "production")
                .register(registry);
    }
	
	@Bean
    public Counter updateUserCreationCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.updated")
                .description("Number of User Updated")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http400ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.UserController.HTTP400")
                .description("How many HTTP Bad Request HTTP 400 Requests have been received since start time of this instance.")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter http404ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.UserController.HTTP404")
                .description("How many HTTP Resource Not Found HTTP 404 Requests have been received since start time of this instance. ")
                .tags("environment", "production")
                .register(registry);
    }
    
	@Bean
    public Counter registerCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.auth.register")
                .description("Number of Registration attempts")
                .tags("environment", "production")
                .register(registry);
    }
	
	@Bean
    public Counter loginCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.auth.login")
                .description("Number of Logins")
                .tags("environment", "production")
                .register(registry);
    }
    
    @Bean
    public Counter AuthHttp400ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.AuthController.HTTP400")
                .description("How many HTTP Bad Request HTTP 400 Requests have been received since start time of this instance.")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public Counter AuthHttp404ExceptionCounter(MeterRegistry registry) {
        return Counter
                .builder("com.fvthree.ecommerce.user.AuthController.HTTP404")
                .description("How many HTTP Resource Not Found HTTP 404 Requests have been received since start time of this instance. ")
                .tags("environment", "production")
                .register(registry);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
