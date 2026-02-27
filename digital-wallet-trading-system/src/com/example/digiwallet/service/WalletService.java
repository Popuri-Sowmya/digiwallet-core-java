package com.example.digiwallet.service;

import java.math.BigDecimal;
import com.example.digiwallet.exception.TradingException;

public interface WalletService {
	void deposit(int userId, BigDecimal amount) throws TradingException;
	void withdraw(int userId, BigDecimal amount) throws TradingException;
	void transfer(int fromUserId, int toUserId, BigDecimal amount) throws TradingException;
}
