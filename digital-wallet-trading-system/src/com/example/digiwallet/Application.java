package com.example.digiwallet;
import java.util.Optional;
import java.math.BigDecimal;

import com.example.digiwallet.dao.*;
import com.example.digiwallet.service.*;
import com.example.digiwallet.model.User;
import com.example.digiwallet.model.Transaction;
import com.example.digiwallet.exception.UserNotFoundException;

public class Application {
	public static void main(String args[]) {
		UserDAO userDAO = new UserDAOImpl();
		TransactionDAO txDao = new TransactionDAOImpl();
		WalletService walSer = new WalletServiceImpl(userDAO,txDao);
		try {
			User userOpt = userDAO.getUserById(3).orElseThrow(()->new UserNotFoundException("User not found"));
			walSer.transfer(1,2,BigDecimal.valueOf(1000));
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
