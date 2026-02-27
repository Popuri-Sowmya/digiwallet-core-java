package com.example.digiwallet.exception;

public class StockNotOwnedException extends TradingException {
	public StockNotOwnedException(String message) {
		super(message);
	}
}
