package com.example.digiwallet.dao;
import com.example.digiwallet.model.User;
import java.util.List;

public interface UserDAO {
	public int createUser(User user);
	public User getUserByEmail(String email);
	public List<User> getAllUsers();
}
