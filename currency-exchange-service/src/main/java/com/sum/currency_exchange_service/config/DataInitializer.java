package com.sum.currency_exchange_service.config;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sum.currency_exchange_service.model.CurrencyExchange;
import com.sum.currency_exchange_service.repository.CurrencyExchangeRepo;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(CurrencyExchangeRepo repository) {
        return args -> {
            repository.save(new CurrencyExchange(1L, "USD", "INR", new BigDecimal("82.50"), ""));
            repository.save(new CurrencyExchange(2L, "EUR", "INR", new BigDecimal("90.00"), ""));
            repository.save(new CurrencyExchange(3L, "GBP", "INR", new BigDecimal("105.25"), ""));
            // add more as needed
        };
    }
}