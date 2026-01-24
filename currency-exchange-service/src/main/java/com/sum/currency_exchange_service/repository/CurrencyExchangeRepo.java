package com.sum.currency_exchange_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sum.currency_exchange_service.model.CurrencyExchange;

public interface CurrencyExchangeRepo extends JpaRepository<CurrencyExchange, Long> {
    Optional<CurrencyExchange> findByFromAndTo(String from, String to);
}
