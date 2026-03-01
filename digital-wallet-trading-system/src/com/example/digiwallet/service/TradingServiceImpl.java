package com.example.digiwallet.service;

import com.example.digiwallet.exception.TradingException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Optional;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.digiwallet.exception.*;
import com.example.digiwallet.dao.*;
import com.example.digiwallet.model.*;
import com.example.digiwallet.util.DBConnection;

public class TradingServiceImpl implements TradingService{
	
	private final UserDAO userDAO;
	private final StockDAO stockDAO;
	private final PortfolioDAO portfolioDAO;
	private final TransactionDAO transactionDAO;

	private final Map<Integer, ReentrantLock> lockMap = new ConcurrentHashMap<>();
	
	public TradingServiceImpl(UserDAO userDAO, StockDAO stockDAO, PortfolioDAO portfolioDAO,
			TransactionDAO transactionDAO) {
		super();
		this.userDAO = userDAO;
		this.stockDAO = stockDAO;
		this.portfolioDAO = portfolioDAO;
		this.transactionDAO = transactionDAO;
	}

	private ReentrantLock getLock(int userId) {
		return lockMap.computeIfAbsent(userId, id->new ReentrantLock(true));
	}
	
	private void rollbackQuietly(Connection con) {
	    if (con != null) {
	        try {
	            con.rollback();
	        } catch (SQLException ignored) {}
	    }
	}

	private void closeQuietly(Connection con) {
	    if (con != null) {
	        try {
	            con.setAutoCommit(true);
	            con.close();
	        } catch (SQLException ignored) {}
	    }
	}

	@Override
	public void buyStock(int userId, String symbol, int quantity) throws TradingException {
		// TODO Auto-generated method stub
		Connection con = null;
		if(quantity<=0) {
			throw new InvalidTransactionException("Invalid quantity");
		}
		ReentrantLock lock = getLock(userId);
		lock.lock();
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			User user = userDAO.getUserById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
			Stock stock = stockDAO.getBySymbol(symbol).orElseThrow(()->new StockNotOwnedException("Stock not found"));
			BigDecimal totalPrice = stock.getPrice().multiply(BigDecimal.valueOf(quantity));
			if(user.getBalance().compareTo(totalPrice)<0) {
				throw new InsufficientBalanceException("Balance not sufficient");
			}
			user.setBalance(user.getBalance().subtract(totalPrice));
			userDAO.update(user,con);
			Optional<Portfolio> existing = portfolioDAO.get(userId,stock.getId());
			if(!existing.isEmpty()) {
				Portfolio portfolio = existing.get();
				portfolio.setQuantity(portfolio.getQuantity()+quantity);
				portfolioDAO.update(portfolio,con);
			}else {
				portfolioDAO.save(new Portfolio(userId,stock.getId(),quantity),con);
			}
			Transaction tx = new Transaction();
			tx.setUserId(userId);
			tx.setType(TransactionType.BUY);
			tx.setAmount(totalPrice);
			transactionDAO.save(tx,con);
			con.commit();
		}catch (TradingException e) {
		    rollbackQuietly(con);
		    throw e;
		}
		catch(Exception e) {
			rollbackQuietly(con);
			throw new TradingException("Buy failed");
		}finally {
			closeQuietly(con);
			lock.unlock();	
		}
	}

	@Override
	public void sellStock(int userId, String symbol, int quantity) throws TradingException {
		// TODO Auto-generated method stub
		Connection con = null;
		if(quantity<=0) {
			throw new InvalidTransactionException("Invalid quantity");
		}
		ReentrantLock lock = getLock(userId);
		lock.lock();
		try {
			con = DBConnection.getConnection();
			con.setAutoCommit(false);
			User user = userDAO.getUserById(userId).orElseThrow(()->new UserNotFoundException("User not found"));
			Stock stock = stockDAO.getBySymbol(symbol).orElseThrow(()->new StockNotOwnedException("Stock not found"));
			BigDecimal totalPrice = stock.getPrice().multiply(BigDecimal.valueOf(quantity));
			user.setBalance(user.getBalance().add(totalPrice));
			userDAO.update(user,con);
			Optional<Portfolio> existing = portfolioDAO.get(userId,stock.getId());
			if(!existing.isEmpty()) {
				Portfolio portfolio = existing.get();
				if (portfolio.getQuantity() < quantity) {
				    throw new StockNotOwnedException("Not enough stock to sell");
				}
				portfolio.setQuantity(portfolio.getQuantity()-quantity);
				portfolioDAO.update(portfolio,con);
			}else {
				throw new StockNotOwnedException("User do not own these stocks");
			}
			Transaction tx = new Transaction();
			tx.setUserId(userId);
			tx.setType(TransactionType.SELL);
			tx.setAmount(totalPrice);
			transactionDAO.save(tx,con);
			con.commit();
		}catch (TradingException e) {
		    rollbackQuietly(con);
		    throw e;
		}
		catch(Exception e) {
			rollbackQuietly(con);
			throw new TradingException("Buy failed");
		}finally {
			closeQuietly(con);
			lock.unlock();	
		}
	}
	
}
