package com.example.digiwallet.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Optional;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.digiwallet.exception.TradingException;
import com.example.digiwallet.exception.InvalidTransactionException;
import com.example.digiwallet.exception.InsufficientBalanceException;
import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.dao.TransactionDAO;
import com.example.digiwallet.model.User;
import com.example.digiwallet.model.Transaction;
import com.example.digiwallet.model.TransactionType;
import com.example.digiwallet.util.DBConnection;

public class WalletServiceImpl implements WalletService{
	
	private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;

    private final Map<Integer, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    
    public WalletServiceImpl(UserDAO userDAO,
            TransactionDAO transactionDAO) {
		this.userDAO = userDAO;
		this.transactionDAO = transactionDAO;
    }

    private ReentrantLock getLock(int userId) {
		return lockMap.computeIfAbsent(userId,
		id -> new ReentrantLock(true));
    }

	@Override
	public void deposit(int userId, BigDecimal amount) throws TradingException {
		if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) {
			throw new InvalidTransactionException("Invalid transaction type");
		}
		ReentrantLock lock = getLock(userId);
		lock.lock();
		try {
			User user = userDAO.getUserById(userId).orElseThrow(()->new TradingException("User not found"));
			user.setBalance(user.getBalance().add(amount));
			userDAO.update(user);
			Transaction tx = new Transaction();
			tx.setUserId(userId);
			tx.setType(TransactionType.DEPOSIT);
			tx.setAmount(amount);
			transactionDAO.save(tx);
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void withdraw(int userId, BigDecimal amount) throws TradingException {
		// TODO Auto-generated method stub
		if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0) {
			throw new InvalidTransactionException("Invalid transaction type");
		}
		ReentrantLock lock = getLock(userId);
		lock.lock();
		try {
			User user = userDAO.getUserById(userId).orElseThrow(()->new TradingException("Error occured"));
			if(user.getBalance().compareTo(amount)<0) {
				throw new InsufficientBalanceException("Balance not sufficient");
			}
			user.setBalance(user.getBalance().subtract(amount));
			userDAO.update(user);
			Transaction tx = new Transaction();
			tx.setUserId(userId);
			tx.setType(TransactionType.WITHDRAW);
			tx.setAmount(amount);
			transactionDAO.save(tx);
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void transfer(int fromUserId, int toUserId, BigDecimal amount) throws TradingException {
		// TODO Auto-generated method stub
		Connection con = null;
		if(fromUserId==toUserId) {
			throw new InvalidTransactionException("Cannot transfer to same account");
		}
		if(amount == null || amount.compareTo(BigDecimal.ZERO)<=0) {
			throw new InvalidTransactionException("Invalid amount");
		}
		int first = Math.min(fromUserId,toUserId);
		int second = Math.max(fromUserId,toUserId);
		ReentrantLock lock1 = getLock(first);
		ReentrantLock lock2 = getLock(second);
		lock1.lock();
		lock2.lock();
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			User fromUser = userDAO.getUserById(fromUserId).orElseThrow(()->new TradingException("User not found"));
			User toUser = userDAO.getUserById(toUserId).orElseThrow(()->new TradingException("User not found"));
			if(fromUser.getBalance().compareTo(amount)<0) {
				throw new InsufficientBalanceException("Insufficient funds");
			}
			fromUser.setBalance(fromUser.getBalance().subtract(amount));
			toUser.setBalance(toUser.getBalance().add(amount));
			userDAO.update(fromUser);
			userDAO.update(toUser);
			
			Transaction from = new Transaction();
			Transaction to = new Transaction();
			from.setUserId(fromUserId);
			from.setType(TransactionType.TRANSFER);
			from.setAmount(amount);
			to.setUserId(toUserId);
			to.setType(TransactionType.TRANSFER);
			to.setAmount(amount);
			transactionDAO.save(from);
			transactionDAO.save(to);
			con.commit();
		}catch(Exception e) {
			if(con != null) {
				try {
					con.rollback();
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw new TradingException("Transfer failed");
		}
		finally {
			lock2.unlock();
			lock1.unlock();
		}
	}
	
}
