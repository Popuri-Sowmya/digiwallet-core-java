package com.example.digiwallet.service;
import com.example.digiwallet.exception.TradingException;

public interface TradingService {
	void buyStock(int userId, String symbol, int quantity)
            throws TradingException;

    void sellStock(int userId, String symbol, int quantity)
            throws TradingException;
}
