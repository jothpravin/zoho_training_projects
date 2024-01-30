package com.stocktradingsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class StockManager extends Thread
{
    private Connection con;
    private int stockCount;
    public StockManager(int stockCount) 
    {
        // Initialize the connection when the StockManager is created
        con = ConnectionDB.getConnection();
        this.stockCount = stockCount;
        try
        {
            if(stockCount == 0)
            {
                sampleStocks();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void run()
    {
        Random random = new Random();
        try
        {
            while(true)
            {
                for(int i=1; i<=stockCount; i++)
                {
                    increaseStockPrice(i, random.nextInt(2));
                    decreaseStockPrice(i, random.nextInt(2));
                }
                Thread.sleep(5000);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void increaseStockPrice(int stockId, double percentageIncrease) throws SQLException 
    {
        updateStockPrice(stockId, 1 + percentageIncrease / 100);
    }

    public void decreaseStockPrice(int stockId, double percentageDecrease) throws SQLException 
    {
        updateStockPrice(stockId, 1 - percentageDecrease / 100);
    }

    private void updateStockPrice(int stockId, double multiplier) throws SQLException 
    {
        String query = "UPDATE Stocks SET stockprice = CASE WHEN (stockprice * ?) < 0 THEN stockprice ELSE stockprice * ? END WHERE stockid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) 
        {
            ps.setDouble(1, multiplier);
            ps.setDouble(2, multiplier);
            ps.setInt(3, stockId);
            ps.executeUpdate();
        }
    }

    private void sampleStocks() throws SQLException
    {
        String query = "INSERT INTO Stocks (stockname, stockprice)"+
                        "VALUES "+
                            "('Reliance Industries', 2500.00),"+
                            "('Tata Consultancy Services', 3500.50),"+
                            "('HDFC Bank', 1400.75),"+
                            "('Infosys', 1800.25),"+
                            "('ITC Limited', 220.50),"+
                            "('Wipro Limited', 600.25),"+
                            "('State Bank of India', 300.00),"+
                            "('Maruti Suzuki India', 7500.00),"+
                            "('Bajaj Finance', 5400.50),"+
                            "('ICICI Bank', 650.75),"+
                            "('Axis Bank', 800.25),"+
                            "('Sun Pharmaceuticals', 450.50),"+
                            "('Bharti Airtel', 550.75),"+
                            "('Hindustan Unilever', 2000.00),"+
                            "('Nestle India', 16000.25),"+
                            "('Asian Paints', 3000.50),"+
                            "('Reliance Technology Ventures', 200.75),"+
                            "('Power Grid Corporation of India', 150.25),"+
                            "('Adani Ports and Special Economic Zone', 800.00),"+
                            "('Mahindra & Mahindra', 750.50)";

        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
