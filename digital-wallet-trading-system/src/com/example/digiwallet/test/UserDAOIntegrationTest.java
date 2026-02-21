package com.example.digiwallet.test;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.digiwallet.dao.*;
import com.example.digiwallet.model.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

public class UserDAOIntegrationTest {
	private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAOImpl();
    }
    
    @Test
    void shouldInsertandFetchUserFromDB() {
    	User user = new User(4,"Real User 4","real.user4@email.com",BigDecimal.valueOf(546));
    	userDAO.createUser(user);
    	Optional<User> fetched = userDAO.getUserByEmail("real.user4@email.com");
    	assertTrue(fetched.isPresent());
    	assertEquals("Real User 4", fetched.get().getName());
    }
    
    @Test
    void shouldFetchAllUsersFromDB() {
    	List<User> allUsers = userDAO.getAllUsers();
    	assertEquals(3,allUsers.size());
    }
}
