package com.stacktradingengine.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.stacktradingengine.dto.StockDTO;
import com.stacktradingengine.entity.Stock;
import com.stacktradingengine.repository.StockRepository;


@Service
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {

        this.repository = repository;
    }

    public Stock create(StockDTO dto) {

        Stock stock = new Stock();

        stock.setSymbol(dto.getSymbol());
        stock.setCompanyName(dto.getCompanyName());

        return repository.save(stock);
    }

    public List<Stock> getAll() {

        return repository.findAll();
    }
}