package com.stacktradingengine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StockDTO {

	private String symbol;

	private String companyName;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
