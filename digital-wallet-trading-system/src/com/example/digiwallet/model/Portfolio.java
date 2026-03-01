package com.example.digiwallet.model;

public class Portfolio {
	private int userId;
	private int stockId;
	private int quantity;
	
	public Portfolio() {
		
	}

	public Portfolio(int userId, int stockId, int quantity) {
		super();
		this.userId = userId;
		this.stockId = stockId;
		this.quantity = quantity;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
