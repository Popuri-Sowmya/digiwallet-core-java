package com.example.digiwallet.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {
	private int id;
	private int userId;
	private TransactionType type;
	private BigDecimal amount;
	private LocalDateTime timestamp;
	
	public Transaction() {
		
	}

	public Transaction(int id, int userId, TransactionType type, BigDecimal amount) {
		super();
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
