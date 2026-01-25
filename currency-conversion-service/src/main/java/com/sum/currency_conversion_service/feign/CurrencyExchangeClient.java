package com.sum.currency_conversion_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sum.currency_conversion_service.model.CurrencyConversion;

// @FeignClient(name = "currency-exchange", url = "localhost:8000")
// With Eureka use just the name of the application
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeClient {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
        @PathVariable("from") String from, 
        @PathVariable("to") String to);
}
