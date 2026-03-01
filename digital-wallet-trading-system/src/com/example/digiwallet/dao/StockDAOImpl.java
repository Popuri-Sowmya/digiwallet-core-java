package com.example.digiwallet.dao;

import java.util.Optional;
import java.sql.*;

import com.example.digiwallet.model.Stock;
import com.example.digiwallet.util.DBConnection;

public class StockDAOImpl implements StockDAO{

	@Override
	public Optional<Stock> getBySymbol(String symbol) {
		String sql = "SELECT * from stocks where symbol = ?";
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setString(1,symbol);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Stock stock = new Stock(rs.getInt("id"),rs.getString("symbol"),rs.getBigDecimal("price"));
				return Optional.of(stock);
			}
			
		}catch(SQLException e) {
			throw new RuntimeException("Error fetching stock from db: "+e);
		}
		return Optional.empty();
	}

}
