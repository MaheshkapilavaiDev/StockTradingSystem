package com.stacktradingengine.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stacktradingengine.dto.OrderBookResponse;
import com.stacktradingengine.dto.OrderRequestDTO;
import com.stacktradingengine.dto.OrderResponseDTO;
import com.stacktradingengine.entity.Order;
import com.stacktradingengine.entity.Stock;
import com.stacktradingengine.entity.Trade;
import com.stacktradingengine.entity.User;
import com.stacktradingengine.enums.OrderStatus;
import com.stacktradingengine.enums.OrderType;
import com.stacktradingengine.repository.OrderRepository;
import com.stacktradingengine.repository.StockRepository;
import com.stacktradingengine.repository.TradeRepository;
import com.stacktradingengine.repository.UserRepository;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final TradeRepository tradeRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        StockRepository stockRepository,
                        TradeRepository tradeRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
        this.tradeRepository = tradeRepository;
    }

    @Transactional
    public Order placeBuyOrder(OrderRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Stock stock = stockRepository.findById(dto.getStockId())
                .orElseThrow(() ->
                        new RuntimeException("Stock not found"));

        Order buyOrder = new Order();

        buyOrder.setUser(user);
        buyOrder.setStock(stock);

        buyOrder.setOrderType(OrderType.BUY);

        buyOrder.setPrice(dto.getPrice());

        buyOrder.setQuantity(dto.getQuantity());

        buyOrder.setRemainingQuantity(dto.getQuantity());

        buyOrder.setStatus(OrderStatus.OPEN);

        orderRepository.save(buyOrder);

        matchBuyOrder(buyOrder);

        return buyOrder;
    }

    @Transactional
    public Order placeSellOrder(OrderRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Stock stock = stockRepository.findById(dto.getStockId())
                .orElseThrow(() ->
                        new RuntimeException("Stock not found"));

        Order sellOrder = new Order();

        sellOrder.setUser(user);
        sellOrder.setStock(stock);

        sellOrder.setOrderType(OrderType.SELL);

        sellOrder.setPrice(dto.getPrice());

        sellOrder.setQuantity(dto.getQuantity());

        sellOrder.setRemainingQuantity(dto.getQuantity());

        sellOrder.setStatus(OrderStatus.OPEN);

        orderRepository.save(sellOrder);

        matchSellOrder(sellOrder);

        return sellOrder;
    }

    @Transactional
    public void matchBuyOrder(Order buyOrder) {

        List<Order> sellOrders =
                orderRepository
                .findByStockAndOrderTypeAndStatusInAndPriceLessThanEqualOrderByPriceAscCreatedAtAsc(
                        buyOrder.getStock(),
                        OrderType.SELL,
                        List.of(
                                OrderStatus.OPEN,
                                OrderStatus.PARTIAL
                        ),
                        buyOrder.getPrice()
                );

        for (Order sellOrder : sellOrders) {

            if (buyOrder.getRemainingQuantity() == 0) {
                break;
            }

            int matchedQuantity =
                    Math.min(
                            buyOrder.getRemainingQuantity(),
                            sellOrder.getRemainingQuantity()
                    );

            buyOrder.setRemainingQuantity(
                    buyOrder.getRemainingQuantity() - matchedQuantity
            );

            sellOrder.setRemainingQuantity(
                    sellOrder.getRemainingQuantity() - matchedQuantity
            );

            updateOrderStatus(buyOrder);

            updateOrderStatus(sellOrder);

            orderRepository.save(buyOrder);

            orderRepository.save(sellOrder);

            Trade trade = new Trade();

            trade.setBuyOrder(buyOrder);

            trade.setSellOrder(sellOrder);

            trade.setPrice(sellOrder.getPrice());

            trade.setQuantity(matchedQuantity);

            tradeRepository.save(trade);
        }
    }

    @Transactional
    public void matchSellOrder(Order sellOrder) {

        List<Order> buyOrders =
                orderRepository
                .findByStockAndOrderTypeAndStatusInAndPriceGreaterThanEqualOrderByPriceDescCreatedAtAsc(
                        sellOrder.getStock(),
                        OrderType.BUY,
                        List.of(
                                OrderStatus.OPEN,
                                OrderStatus.PARTIAL
                        ),
                        sellOrder.getPrice()
                );

        for (Order buyOrder : buyOrders) {

            if (sellOrder.getRemainingQuantity() == 0) {
                break;
            }

            int matchedQuantity =
                    Math.min(
                            sellOrder.getRemainingQuantity(),
                            buyOrder.getRemainingQuantity()
                    );

            sellOrder.setRemainingQuantity(
                    sellOrder.getRemainingQuantity() - matchedQuantity
            );

            buyOrder.setRemainingQuantity(
                    buyOrder.getRemainingQuantity() - matchedQuantity
            );

            updateOrderStatus(sellOrder);

            updateOrderStatus(buyOrder);

            orderRepository.save(sellOrder);

            orderRepository.save(buyOrder);

            Trade trade = new Trade();

            trade.setBuyOrder(buyOrder);

            trade.setSellOrder(sellOrder);

            trade.setPrice(sellOrder.getPrice());

            trade.setQuantity(matchedQuantity);

            tradeRepository.save(trade);
        }
    }

    private void updateOrderStatus(Order order) {

        if (order.getRemainingQuantity() == 0) {

            order.setStatus(OrderStatus.FILLED);
        }

        else if (order.getRemainingQuantity()
                < order.getQuantity()) {

            order.setStatus(OrderStatus.PARTIAL);
        }

        else {

            order.setStatus(OrderStatus.OPEN);
        }
    }

    public List<Order> userOrders(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public void cancel(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);
    }

    public OrderBookResponse getOrderBook(String symbol) {

        List<OrderStatus> activeStatuses =
                List.of(OrderStatus.OPEN, OrderStatus.PARTIAL);

        List<Order> buyOrders =
                orderRepository
                .findByStockSymbolAndOrderTypeAndStatusInOrderByPriceDescCreatedAtAsc(
                        symbol,
                        OrderType.BUY,
                        activeStatuses);

        List<Order> sellOrders =
                orderRepository
                .findByStockSymbolAndOrderTypeAndStatusInOrderByPriceAscCreatedAtAsc(
                        symbol,
                        OrderType.SELL,
                        activeStatuses);

        OrderBookResponse response = new OrderBookResponse();

        response.setBuyOrders(buyOrders);
        response.setSellOrders(sellOrders);

        return response;
    }
    
    private OrderResponseDTO mapToDTO(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setId(order.getId());

        dto.setStockSymbol(order.getStock().getSymbol());

        dto.setUserName(order.getUser().getName());

        dto.setOrderType(order.getOrderType());

        dto.setPrice(order.getPrice());

        dto.setQuantity(order.getQuantity());

        dto.setRemainingQuantity(order.getRemainingQuantity());

        dto.setStatus(order.getStatus());

        dto.setCreatedAt(order.getCreatedAt());

        return dto;
    }
}