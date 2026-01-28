package com.sum.currency_exchange_service.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sum.currency_exchange_service.model.CurrencyExchange;
import com.sum.currency_exchange_service.repository.CurrencyExchangeRepo;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepo repo;

    Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
        @PathVariable String from,
        @PathVariable String to
    ) {
        // String port = environment.getProperty("local.server.port");

        // return new CurrencyExchange(1001L, from, to, BigDecimal.valueOf(65), port);

        CurrencyExchange currencyExchange = repo.findByFromAndTo(from, to).orElse(new CurrencyExchange());
        currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyExchange;
    }
}
