package com.example.digiwallet.dao;

import java.sql.Connection;
import java.util.Optional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.digiwallet.model.Portfolio;
import com.example.digiwallet.util.DBConnection;

public class PortfolioDAOImpl implements PortfolioDAO{

	@Override
	public Optional<Portfolio> get(int userId, int stockId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM portfolio where user_id = ? and stock_id = ?";
		try(Connection con = DBConnection.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1,userId);
			ps.setInt(2,stockId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				Portfolio portfolio = new Portfolio();
				portfolio.setUserId(rs.getInt("user_id"));
				portfolio.setStockId(rs.getInt("stock_id"));
				portfolio.setQuantity(rs.getInt("quantity"));
				return Optional.of(portfolio);
			}
		}catch(SQLException e) {
			throw new RuntimeException("Error while fetching portfolio from db: "+e);
		}
		return Optional.empty();
	}

	@Override
	public void save(Portfolio portfolio, Connection con) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO portfolio (user_id,stock_id,quantity) "+"VALUES (?,?,?)";
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1,portfolio.getUserId());
			ps.setInt(2,portfolio.getStockId());
			ps.setInt(3,portfolio.getQuantity());
			ps.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException("Error while inserting portfolio from db: "+e);
		}
	}

	@Override
	public void update(Portfolio portfolio, Connection con) {
		// TODO Auto-generated method stub
		String sql = "UPDATE portfolio set quantity = ? where user_id = ? and stock_id = ?";
		try(PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1,portfolio.getQuantity());
			ps.setInt(2,portfolio.getUserId());
			ps.setInt(3,portfolio.getStockId());
			ps.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException("Error while inserting portfolio from db: "+e);
		}
	}

}
