package com.example.digiwallet.service;

import java.util.List;
import java.util.Optional;

import com.example.digiwallet.exception.DuplicateEmailException;
import com.example.digiwallet.exception.UserNotFoundException;
import com.example.digiwallet.model.User;
import com.example.digiwallet.dao.UserDAO;

public class UserServiceImpl implements IUserService{
	
	private UserDAO userDAO;

	public UserServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	@Override
	public void createUser(User user) throws DuplicateEmailException {
		// TODO Auto-generated method stub
		Optional<User> existingUser = userDAO.getUserByEmail(user.getEmail());
		if(existingUser.isPresent()) {
			throw new DuplicateEmailException("User Already Exists");
		}
		userDAO.createUser(user);
	}

	@Override
	public User getUserByEmail(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return userDAO.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDAO.getAllUsers();
	}

}
