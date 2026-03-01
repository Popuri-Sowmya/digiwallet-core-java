package com.example.digiwallet.dao;
import java.util.Optional;
import com.example.digiwallet.model.Stock;

public interface StockDAO {
	Optional<Stock> getBySymbol(String symbol);
}
