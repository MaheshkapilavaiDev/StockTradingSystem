package com.stacktradingengine.dto;

import java.util.List;

import com.stacktradingengine.entity.Order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBookResponse {

	private List<Order> buyOrders;

	private List<Order> sellOrders;

	public List<Order> getBuyOrders() {
		return buyOrders;
	}

	public void setBuyOrders(List<Order> buyOrders) {
		this.buyOrders = buyOrders;
	}

	public List<Order> getSellOrders() {
		return sellOrders;
	}

	public void setSellOrders(List<Order> sellOrders) {
		this.sellOrders = sellOrders;
	}
	
	
}


