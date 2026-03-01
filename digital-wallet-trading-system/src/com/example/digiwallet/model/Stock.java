package com.example.digiwallet.model;
import java.math.BigDecimal;

public class Stock {
	private int id;
	private String symbol;
	private BigDecimal price;
	
	public Stock() {
		
	}

	public Stock(int id, String symbol, BigDecimal price) {
		super();
		this.id = id;
		this.symbol = symbol;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
