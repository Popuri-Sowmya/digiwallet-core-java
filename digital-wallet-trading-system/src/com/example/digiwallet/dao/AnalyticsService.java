package com.example.digiwallet.dao;
import java.math.BigDecimal;
import java.util.List;
import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.model.User;

public class AnalyticsService {
	private final UserDAO userDAO;
	
	public AnalyticsService(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public BigDecimal getTotalBalance() {
		return userDAO.getAllUsers().stream().map(User::getBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	
	public List<User> getTop3RichestUsers() {
		return userDAO.getAllUsers().stream().sorted((u1,u2)->u2.getBalance().compareTo(u1.getBalance())).limit(3).toList();
	}
}
