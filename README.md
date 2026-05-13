# Stock Trading Order Engine

## Project Overview

Stock Trading Order Engine is a backend application developed using Spring Boot.

The project simulates the core functionality of a stock trading platform where users can:

- Register and manage accounts
- View available stocks
- Place BUY and SELL orders
- Match orders automatically
- Execute trades
- Maintain an order book
- Track order history and trade history

The application follows layered architecture using:

- Controller Layer
- Service Layer
- Repository Layer
- DTO Pattern
- JWT Authentication
- Exception Handling

---

# Features

## User Management
- User Registration
- User Login using JWT
- Role-based Authentication (ADMIN / USER)

## Stock Management
- Add Stocks
- View All Stocks

## Order Management
- Place BUY Orders
- Place SELL Orders
- Cancel Orders
- View User Order History

## Trading Engine
- Automatic Order Matching
- FIFO Matching Logic
- Partial Order Matching
- Trade Execution Handling

## Order Book
- Separate BUY and SELL Order Books
- View Market Depth for Stocks

## Trade Management
- Store Executed Trades
- View Recent Trades

## Security
- JWT Authentication
- Password Encryption using BCrypt
- Role-based Authorization

## Documentation
- Swagger / OpenAPI Documentation

---

# Technologies Used

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok
- Swagger OpenAPI

---

# Database Tables

## User
- id
- name
- email
- password
- role
- balance
- created_at

## Stock
- id
- symbol
- company_name

## Order
- id
- user_id
- stock_id
- order_type
- price
- quantity
- remaining_quantity
- status
- created_at

## Trade
- id
- buy_order_id
- sell_order_id
- price
- quantity
- executed_at

---

# API Endpoints

## Authentication APIs

### Register User
POST `/auth/register`

### Login
POST `/auth/login`

---

## Stock APIs

### Add Stock
POST `/stocks`

### Get All Stocks
GET `/stocks`

---

## Order APIs

### Place Buy Order
POST `/orders/buy`

### Place Sell Order
POST `/orders/sell`

### Cancel Order
DELETE `/orders/{id}`

### User Order History
GET `/orders/user/{userId}`

### Order Book
GET `/orders/orderbook/{symbol}`

---

## Trade APIs

### Get Trades by Stock
GET `/trades/stock/{symbol}`

---

# Swagger Documentation

Swagger URL:

http://localhost:8080/swagger-ui/index.html

---

# Default Admin

Admin user is automatically created when application starts.

## Default Admin Credentials

Email: admin@gmail.com  
Password: admin123

---

# Project Flow

1. User Registers
2. User Logs In
3. JWT Token Generated
4. User Adds / Views Stocks
5. User Places BUY or SELL Orders
6. Trading Engine Matches Orders
7. Trades are Executed
8. Order Status Updated
9. User Can View Order Book and Trade History

---

# Order Status

- OPEN
- PARTIAL
- FILLED
- CANCELLED

---

# Order Types

- BUY
- SELL

---

Create MySQL Database

sql
CREATE DATABASE stock_trading_engine;

http://localhost:8080/swagger-ui/index.html

Author

Mahesh Kapilavai