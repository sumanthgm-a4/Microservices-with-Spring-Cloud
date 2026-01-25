package com.sum.currency_exchange_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("sample")
    // @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    // @CircuitBreaker(name = "sample-api", fallbackMethod = "hardcodedResponse")
    // @RateLimiter(name = "sample-api")
    @Bulkhead(name = "sample-api")
    public String sampleAPI() {

        logger.info("Sample API call received");

        // ResponseEntity<String> response = new RestTemplate()
        //     .getForEntity("http://localhost:8080/some-dummy-url", 
        //     String.class);

        // For BulkHead testing purpose
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Sample API is working fine";
    }

    public String hardcodedResponse(Exception ex) {
        return "Fallback response: Service is currently unavailable.";
    }
}

