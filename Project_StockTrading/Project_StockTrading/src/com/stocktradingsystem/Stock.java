package com.stocktradingsystem;

public class Stock 
{
    private int stockId;
    private String stockName;
    private double price;

    public Stock(String stockName, double price) 
    {
        this.stockId = 0;
        this.stockName = stockName;
        this.price = price;
    }
    public int getStockId() 
    {
        return stockId;
    }
    public void setStockId(int stockId) 
    {
        this.stockId = stockId;
    }
    public String getStockName() 
    {
        return stockName;
    }
    public double getPrice() 
    {
        return price;
    }

    public String toString()
    {
        return "Stock Id :"+stockId+"\n"+"Stock Name :"+stockName+"\n"+"Stock Price :"+price;
    }
}
