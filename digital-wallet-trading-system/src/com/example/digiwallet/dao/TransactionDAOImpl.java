package com.example.digiwallet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.digiwallet.model.Transaction;
import com.example.digiwallet.util.DBConnection;
import com.example.digiwallet.model.TransactionType;

public class TransactionDAOImpl implements TransactionDAO{

	@Override
	public int save(Transaction transaction) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO transactions (user_id,type,amount) " +
				"VALUES (?, ?,?)";
		try(Connection con = DBConnection.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			preparedStatement.setInt(1,transaction.getUserId());
			preparedStatement.setObject(2, transaction.getType().name(), java.sql.Types.OTHER);
			preparedStatement.setBigDecimal(3,transaction.getAmount());
			int rowsInserted = preparedStatement.executeUpdate();
			return rowsInserted;
		}catch(SQLException e) {
			throw new RuntimeException("Error while inserting transaction in db: ",e);
		}
	}

	@Override
	public List<Transaction> getByUserId(int userId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from transactions where user_id = ?";
		List<Transaction> transactions = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			preparedStatement.setInt(1,userId);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt("id"));
				transaction.setUserId(rs.getInt("user_id"));
				transaction.setType(TransactionType.valueOf(rs.getString("type")));
				transaction.setAmount(rs.getBigDecimal("amount"));
				transaction.setTimestamp(rs.getTimestamp("transaction_time").toLocalDateTime());
				transactions.add(transaction);
			}
		}catch(SQLException e) {
			throw new RuntimeException("Error while fetching the transactions in db: ",e);
		}
		return transactions;
	}

	@Override
	public List<Transaction> getAll() {
		// TODO Auto-generated method stub
		String sql = "SELECT * from transactions";
		List<Transaction> transactions = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setId(rs.getInt("id"));
				transaction.setUserId(rs.getInt("user_id"));
				transaction.setType(TransactionType.valueOf(rs.getString("type")));
				transaction.setAmount(rs.getBigDecimal("amount"));
				transaction.setTimestamp(rs.getTimestamp("transaction_time").toLocalDateTime());
				transactions.add(transaction);
			}
		}catch(SQLException e) {
			throw new RuntimeException("Error while fetching the transaction in db: ",e);
		}
		return transactions;
	}

}
