package com.example.digiwallet.dao;
import com.example.digiwallet.model.User;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;

public interface UserDAO {
	public int createUser(User user);
	public Optional<User> getUserByEmail(String email);
	public List<User> getAllUsers();
	Optional<User> getUserById(int id);
	int update(User user);
	int update(User user,Connection con);
}
