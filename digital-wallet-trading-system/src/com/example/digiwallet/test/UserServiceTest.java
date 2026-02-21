package com.example.digiwallet.test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.digiwallet.dao.UserDAO;
import com.example.digiwallet.service.*;
import com.example.digiwallet.model.User;
import com.example.digiwallet.exception.*;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

public class UserServiceTest {
	private IUserService service;
	@BeforeEach
    void setUp() {
        UserDAO dao = new InMemoryUserDAO();
        service = new UserServiceImpl(dao);
    }
	
	@Test
    void shouldCreateUserSuccessfully() throws Exception {

        User user = new User(1, "Sowmya", "sowmya@mail.com", BigDecimal.ZERO);

        service.createUser(user);

        assertEquals("Sowmya",
            service.getUserByEmail("sowmya@mail.com").getName());
    }
	
	@Test
	void shouldThrowExceptionIfEmailAlreadyExists() throws Exception {

	    User user = new User(1, "Sowmya", "dup@mail.com", BigDecimal.ZERO);

	    service.createUser(user);

	    assertThrows(DuplicateEmailException.class, () -> {
	        service.createUser(user);
	    });
	}
	
	@Test
	void shouldThrowExceptionWhenUserNotFound() throws Exception {
	    assertThrows(UserNotFoundException.class, () -> {
	        service.getUserByEmail("random@gmail.com");
	    });
	    
	}
	
	@Test
	void shouldReturnAllUsers() throws Exception{
		service.createUser(new User(1, "A", "a@mail.com", BigDecimal.ZERO));
	    service.createUser(new User(2, "B", "b@mail.com", BigDecimal.ZERO));

	    assertEquals(2, service.getAllUsers().size());
	}


}
