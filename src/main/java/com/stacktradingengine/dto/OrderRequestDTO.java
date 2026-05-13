package com.stacktradingengine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderRequestDTO {

	private Long userId;

	private Long stockId;

	private Double price;

	private Integer quantity;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
