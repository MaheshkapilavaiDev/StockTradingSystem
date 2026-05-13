package com.stacktradingengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stacktradingengine.dto.StockDTO;
import com.stacktradingengine.entity.Stock;
import com.stacktradingengine.service.StockService;

@RestController
@RequestMapping("/stocks")

public class StockController {

	private final StockService service;

	public StockController(StockService service) {

		this.service = service;
	}

	@PostMapping("/add")
	public Stock create(@RequestBody StockDTO dto) {

		return service.create(dto);
	}

	@GetMapping("/getAllStock")
	public List<Stock> getAll() {

		return service.getAll();
	}
}
