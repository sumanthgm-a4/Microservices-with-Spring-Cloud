package com.sum.currency_conversion_service.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sum.currency_conversion_service.feign.CurrencyExchangeClient;
import com.sum.currency_conversion_service.model.CurrencyConversion;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeClient currencyExchangeClient;

    @GetMapping(value = "/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity
    ) {
        // Using Rest Template

        Map<String, String> uriVariables = Map.of(
            "from", from,
            "to", to
        );

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
            "http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
            CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, currencyConversion.getConversionMultiple(), 
            quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " " + "rest template");
    }

    @GetMapping(value = "/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
        @PathVariable String from,
        @PathVariable String to,
        @PathVariable BigDecimal quantity
    ) {
        // Using Feign Client
        CurrencyConversion currencyConversion = currencyExchangeClient.retrieveExchangeValue(from, to);
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, currencyConversion.getConversionMultiple(), 
            quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " " + "feign");
    }
}
