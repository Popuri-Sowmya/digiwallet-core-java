package com.example.digiwallet.dao;
import com.example.digiwallet.model.Portfolio;
import java.util.Optional;
import java.sql.Connection;

public interface PortfolioDAO {
	Optional<Portfolio> get(int userId, int stockId);
	void save(Portfolio portfolio, Connection con);
	void update(Portfolio portfolio, Connection con);
}
