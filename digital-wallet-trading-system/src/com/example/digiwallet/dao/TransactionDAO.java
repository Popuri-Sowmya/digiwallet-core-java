package com.example.digiwallet.dao;
import java.util.List;
import com.example.digiwallet.model.Transaction;
import java.sql.Connection;

public interface TransactionDAO {

    int save(Transaction transaction);

    List<Transaction> getByUserId(int userId);

    List<Transaction> getAll();
    
    int save(Transaction transaction, Connection con);
}
