package com.stacktradingengine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stacktradingengine.dto.OrderBookResponse;
import com.stacktradingengine.dto.OrderRequestDTO;
import com.stacktradingengine.entity.Order;
import com.stacktradingengine.service.OrderService;

@RestController
@RequestMapping("/orders")

public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {

		this.service = service;
	}

	@PostMapping("/buy")
	public Order buy(@RequestBody OrderRequestDTO dto) {

		return service.placeBuyOrder(dto);
	}

	@PostMapping("/sell")
	public Order sell(@RequestBody OrderRequestDTO dto) {

		return service.placeSellOrder(dto);
	}

	@GetMapping("/user/{userId}")
	public List<Order> history(@PathVariable Long userId) {

		return service.userOrders(userId);
	}
	
	@GetMapping("/orderbook/{symbol}")
	public OrderBookResponse getOrderBook(@PathVariable String symbol) {

	    return service.getOrderBook(symbol);
	}

	@DeleteMapping("/{id}")
	public String cancel(@PathVariable Long id) {

		service.cancel(id);

		return "Order cancelled";
	}
}
