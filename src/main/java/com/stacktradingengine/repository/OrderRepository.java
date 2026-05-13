package com.stacktradingengine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacktradingengine.entity.Order;
import com.stacktradingengine.entity.Stock;
import com.stacktradingengine.enums.OrderStatus;
import com.stacktradingengine.enums.OrderType;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    // MATCHING SELL ORDERS FOR BUY
    List<Order>
    findByStockAndOrderTypeAndStatusInAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
            Stock stock,
            OrderType orderType,
            List<OrderStatus> statuses,
            Double price
    );

    // MATCHING BUY ORDERS FOR SELL
    List<Order>
    findByStockAndOrderTypeAndStatusInAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
            Stock stock,
            OrderType orderType,
            List<OrderStatus> statuses,
            Double price
    );

    // ORDER BOOK BUY SIDE
    List<Order>
    findByStockSymbolAndOrderTypeAndStatusInOrderByPriceDescCreatedAtAsc(
            String symbol,
            OrderType orderType,
            List<OrderStatus> statuses);

    // ORDER BOOK SELL SIDE
    List<Order>
    findByStockSymbolAndOrderTypeAndStatusInOrderByPriceAscCreatedAtAsc(
            String symbol,
            OrderType orderType,
            List<OrderStatus> statuses);
}