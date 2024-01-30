package com.stocktradingsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper 
{
    private Connection con = null;
    private static Statement stm = null;

    public Helper() 
    {
        try 
        {
            con = ConnectionDB.getConnection();
            stm = con.createStatement();
            createDematSequenece();
            createEnumTypes();
            createUsersTable();
            createTradingAccount();
            createStocksTable();
            createHoldingsTable();
            createTransactionsTable();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            ConnectionDB.closeConnection();
        }
    }

    private static void executeQuery(String query) throws SQLException 
    {
        stm.executeUpdate(query);
    }

    private static void createEnumTypes() throws SQLException 
    {
        createEnumType("gender_enum", "'Male', 'Female'");
        createEnumType("status_enum", "'Buy', 'Sell'");
    }

    private static void createEnumType(String typeName, String values) throws SQLException 
    {
        if(!typeExists(typeName)) 
        {
            String createQuery = "CREATE TYPE " + typeName + " AS ENUM (" + values + ")";
            executeQuery(createQuery);
        }
    }

    private static boolean typeExists(String typeName) throws SQLException 
    {
        String checkQuery = "SELECT 1 FROM pg_type WHERE typname = '" + typeName + "'";
        try (ResultSet resultSet = stm.executeQuery(checkQuery)) 
        {
            return resultSet.next();
        }
    }

    private static void createUsersTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "userId SERIAL PRIMARY KEY," +
                "fName VARCHAR(50)," +
                "lName VARCHAR(50)," +
                "email VARCHAR(150)," +
                "password VARCHAR(50)," +
                "mobile_no BIGINT," +
                "dob DATE," +
                "gender gender_enum " +
                ")";
        executeQuery(query);
    }

    private static void createDematSequenece() throws SQLException
    {
        String query = "CREATE SEQUENCE IF NOT EXISTS demat_acc_no_seq START WITH 324900010";
        executeQuery(query);
    }

    private static void createTradingAccount() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS TradingAccount (" +
                        "taId SERIAL PRIMARY KEY, " +
                        "userId INTEGER REFERENCES Users(userId)," +
                        "aadhar_no BIGINT," +
                        "pan_no VARCHAR(20)," +
                        "demat_acc_no BIGINT DEFAULT nextval('demat_acc_no_seq') NOT NULL," +
                        "amount DECIMAL (10, 2) NOT NULL"+
                        ")";
        executeQuery(query);
    }

    private static void createStocksTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Stocks (" +
                "stockId SERIAL PRIMARY KEY," +
                "stockName VARCHAR(100)," +
                "stockPrice DECIMAL (10, 2) NOT NULL" +
                ")";
        executeQuery(query);
    }

    private static void createHoldingsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Holdings (" +
                "holdingsId SERIAL PRIMARY KEY," +
                "taId INTEGER REFERENCES TradingAccount(taId)," +
                "stockId INTEGER REFERENCES Stocks(stockId)," +
                "amount DECIMAL (10, 2) NOT NULL," +
                "price DECIMAL (10, 2) NOT NULL," +
                "quantity INTEGER NOT NULL, " +
                "quantity_selled INTEGER, " +
                "hold_date DATE default current_date"+
                ")";
        executeQuery(query);
    }

    private static void createTransactionsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Transactions (" +
                "id SERIAL PRIMARY KEY," +
                "taId INTEGER REFERENCES TradingAccount(taId)," +
                "stockId INTEGER REFERENCES Stocks(stockId)," +
                "action status_enum," +
                "amount DECIMAL (10, 2) NOT NULL," +
                "price DECIMAL (10, 2) NOT NULL," +
                "quantity INTEGER NOT NULL DEFAULT 0," +
                "transaction_date DATE DEFAULT current_date" +
                ")";
        executeQuery(query);
    }
}
