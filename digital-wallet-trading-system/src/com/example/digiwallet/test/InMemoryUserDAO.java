package com.example.digiwallet.test;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.util.ArrayList;

import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.model.User;

public class InMemoryUserDAO implements UserDAO{
	
	private Map<String,User> users = new HashMap<>();

	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		users.put(user.getEmail(),user);
		return 0;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(users.get(email));
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return new ArrayList<>(users.values());
	}

	@Override
	public Optional<User> getUserById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User user, Connection con) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
