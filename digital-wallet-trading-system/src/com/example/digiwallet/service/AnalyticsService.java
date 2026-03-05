package com.example.digiwallet.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.digiwallet.model.Portfolio;
import com.example.digiwallet.model.User;
import com.example.digiwallet.dao.UserDAO;


public class AnalyticsService {
	
	public Map<Integer, Integer> totalQuantityPerStock(List<Portfolio> portfolios){
		return portfolios.stream().collect(Collectors.groupingBy(Portfolio::getStockId,Collectors.summingInt(Portfolio::getQuantity)));
	}
	
	public List<Integer> uniqueStockIds(List<Portfolio> portfolios){
		return portfolios.stream().map(Portfolio::getStockId).distinct().toList();
	}
	
	public BigDecimal getTotalBalance(List<User> users) {
	    return users.stream()
	            .map(User::getBalance)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public List<User> getTop3RichestUsers(List<User> users) {
	    return users.stream()
	            .sorted(Comparator.comparing(User::getBalance).reversed())
	            .limit(3)
	            .toList();
	}
	
	public Optional<User> richestUser(List<User> users){
		return users.stream().collect(Collectors.maxBy(Comparator.comparing(User::getBalance)));
	}
}
