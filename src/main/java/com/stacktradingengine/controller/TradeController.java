package com.stacktradingengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stacktradingengine.entity.Trade;
import com.stacktradingengine.service.TradeService;

@RestController
@RequestMapping("/trades")

public class TradeController {

	private final TradeService service;

	public TradeController(TradeService service) {

		this.service = service;
	}

	@GetMapping("/stock/{symbol}")
	public List<Trade> recent(@PathVariable String symbol) {

		return service.recentTrades(symbol);
	}
}