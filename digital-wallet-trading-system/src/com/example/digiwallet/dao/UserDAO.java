package com.example.digiwallet.dao;
import com.example.digiwallet.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
	public int createUser(User user);
	public Optional<User> getUserByEmail(String email);
	public List<User> getAllUsers();
}
