package com.example.digiwallet.service;
import java.sql.Connection;
import java.util.List;

import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.model.User;
import com.example.digiwallet.util.DBConnection;

public class UserDAOImpl implements UserDAO{
	try{
		Connection con = DBConnection.getConnection();
	}catch(SQLException e) {
		
	}
	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
