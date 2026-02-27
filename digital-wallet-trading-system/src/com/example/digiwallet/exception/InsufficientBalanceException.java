package com.example.digiwallet.exception;

public class InsufficientBalanceException extends TradingException {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
