package com.stacktradingengine.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacktradingengine.entity.Trade;


public interface TradeRepository
        extends JpaRepository<Trade, Long> {

    List<Trade> findTop10ByBuyOrderStockSymbolOrderByExecutedAtDesc(
			String symbol);
}
