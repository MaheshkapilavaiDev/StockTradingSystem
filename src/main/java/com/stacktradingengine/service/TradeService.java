package com.stacktradingengine.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stacktradingengine.entity.Trade;
import com.stacktradingengine.repository.TradeRepository;

@Service
public class TradeService {

	private final TradeRepository repository;

	public TradeService(TradeRepository repository) {

		this.repository = repository;
	}

	public List<Trade> recentTrades(String symbol) {

		return repository.findTop10ByBuyOrderStockSymbolOrderByExecutedAtDesc(symbol);
	}
}
