package com.example.digiwallet.dao;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.model.User;
import com.example.digiwallet.util.DBConnection;
import java.util.Optional;

public class UserDAOImpl implements UserDAO{

	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO users (name, email, balance) " +
				"VALUES (?, ?, ?)";
		try(Connection con = DBConnection.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			preparedStatement.setString(1,user.getName());
			preparedStatement.setString(2,user.getEmail());
			preparedStatement.setBigDecimal(3,user.getBalance());
			int rowsInserted = preparedStatement.executeUpdate();
			return rowsInserted;
		}catch(SQLException e) {
			throw new RuntimeException("Error while inserting user in db: ",e);
		}
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from users where email = (?)";
		User user = new User();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			preparedStatement.setString(1,email);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setBalance(rs.getBigDecimal("balance"));
				return Optional.of(user);
			}
			return Optional.empty();
		}catch(SQLException e) {
			throw new RuntimeException("Error while fetching the user in db: ",e);
		}
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		String sql = "SELECT * from users";
		List<User> users = new ArrayList<>();
		try(Connection con = DBConnection.getConnection();
				PreparedStatement preparedStatement = con.prepareStatement(sql)	){
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setBalance(rs.getBigDecimal("balance"));
				users.add(user);
			}
		}catch(SQLException e) {
			throw new RuntimeException("Error while fetching the user in db: ",e);
		}
		return users;
	}

}
