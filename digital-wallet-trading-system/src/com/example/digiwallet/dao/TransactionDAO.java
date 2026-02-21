package com.example.digiwallet.dao;
import java.util.List;
import com.example.digiwallet.model.Transaction;

public interface TransactionDAO {

    int save(Transaction transaction);

    List<Transaction> getByUserId(int userId);

    List<Transaction> getAll();
}
