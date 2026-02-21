package com.example.digiwallet.service;
import com.example.digiwallet.model.User;
import com.example.digiwallet.exception.DuplicateEmailException;
import com.example.digiwallet.exception.UserNotFoundException;
import java.util.Optional;
import java.util.List;

public interface IUserService {
	void createUser(User user) throws DuplicateEmailException;
	User getUserByEmail(String email) throws UserNotFoundException;
	List<User> getAllUsers();
}
