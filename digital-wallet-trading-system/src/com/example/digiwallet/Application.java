package com.example.digiwallet;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.Random;

import com.example.digiwallet.dao.*;
import com.example.digiwallet.service.*;
import com.example.digiwallet.model.User;
import com.example.digiwallet.model.Transaction;
import com.example.digiwallet.exception.UserNotFoundException;

public class Application {
	public static void main(String args[]) {
		UserDAO userDAO = new UserDAOImpl();
		TransactionDAO txDao = new TransactionDAOImpl();
		StockDAO stockDAO = new StockDAOImpl();
		PortfolioDAO ptfDAO = new PortfolioDAOImpl();
		WalletService walletService = new WalletServiceImpl(userDAO,txDao);
		TradingService transactionService = new TradingServiceImpl(userDAO,stockDAO,ptfDAO,txDao);
		try{
			transactionService.buyStock(3,"GOOG",10);
		}catch(Exception e) {
			e.printStackTrace();
		}
//		ExecutorService executor = Executors.newFixedThreadPool(20);
//		Random random = new Random();
//		try {
//			for (int i = 1; i <= 5; i++) {
//			    User user = userDAO.getUserById(i)
//			            .orElseThrow(() -> new RuntimeException("User not found"));
//
//			    user.setBalance(new BigDecimal("1000"));
//			    userDAO.update(user);
//			}
//			BigDecimal totalBefore = BigDecimal.ZERO;
//
//			for (int i = 1; i <= 5; i++) {
//			    totalBefore = totalBefore.add(
//			            userDAO.getUserById(i).get().getBalance()
//			    );
//			}
//			System.out.println("Total BEFORE transfers: " + totalBefore);
//			walletService.deposit(1, BigDecimal.valueOf(1000));
//			walletService.deposit(2, BigDecimal.valueOf(1000));
//			walletService.deposit(3, BigDecimal.valueOf(1000));
//			walletService.deposit(4, BigDecimal.valueOf(1000));
//			walletService.deposit(5, BigDecimal.valueOf(1000));
//			for (int i = 0; i < 20; i++) {
//			    executor.submit(() -> {
//			        int from = random.nextInt(5) + 1;
//			        int to = random.nextInt(5) + 1;
//
//			        if (from != to) {
//			            try {
//			                walletService.transfer(
//			                    from,
//			                    to,
//			                    new BigDecimal("10")
//			                );
//			            } catch (Exception e) {
//			                System.out.println("Transfer failed");
//			            }
//			        }
//			    });
//			}
//
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}finally {
//			executor.shutdown();
//			try {
//			    executor.awaitTermination(1, TimeUnit.MINUTES);
//			} catch (InterruptedException e) {
//			    e.printStackTrace();
//			}
//		}
//		BigDecimal totalAfter = BigDecimal.ZERO;
//
//		for (int i = 1; i <= 5; i++) {
//		    User user = userDAO.getUserById(i).get();
//		    System.out.println("User " + i + " balance: " + user.getBalance());
//		    totalAfter = totalAfter.add(user.getBalance());
//		}
//		
//		System.out.println("Total AFTER transfers: " + totalAfter);
	}
}
