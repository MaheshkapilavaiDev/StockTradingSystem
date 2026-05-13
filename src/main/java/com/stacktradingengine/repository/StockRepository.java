package com.stacktradingengine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacktradingengine.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	Optional<Stock> findBySymbol(String symbol);
}
