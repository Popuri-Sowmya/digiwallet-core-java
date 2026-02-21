package com.example.digiwallet.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.digiwallet.dao.*;
import com.example.digiwallet.model.*;

import java.math.BigDecimal;
import java.util.List;

class TransactionDAOIntegrationTest {

    private TransactionDAO transactionDAO;
    private UserDAO userDAO;

    private int testUserId = 4;

    @BeforeEach
    void setUp() {
        transactionDAO = new TransactionDAOImpl();
        userDAO = new UserDAOImpl();

        // Ensure test user exists
        User user = new User(5, "TestUser", "test_tx@mail.com", BigDecimal.ZERO);
        try {
            userDAO.createUser(user);
        } catch (Exception ignored) {}

        // Fetch user to get ID (adjust if needed)
        testUserId = userDAO.getUserByEmail("test_tx@mail.com")
                             .get()
                             .getId();
    }

    @Test
    void shouldInsertTransactionSuccessfully() {

        Transaction tx = new Transaction();
        tx.setUserId(testUserId);
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(BigDecimal.valueOf(500));

        int rows = transactionDAO.save(tx);

        assertEquals(1, rows);
    }

    @Test
    void shouldFetchTransactionsByUserId() {

        Transaction tx = new Transaction();
        tx.setUserId(testUserId);
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(BigDecimal.valueOf(300));

        transactionDAO.save(tx);

        List<Transaction> transactions =
                transactionDAO.getByUserId(testUserId);

        assertFalse(transactions.isEmpty());
    }

    @Test
    void shouldFetchAllTransactions() {

        List<Transaction> transactions =
                transactionDAO.getAll();

        assertNotNull(transactions);
    }
}
