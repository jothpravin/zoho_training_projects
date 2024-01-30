package com.stocktradingsystem;

import java.util.Date;

public class Holding 
{
    private int holdId;
    private int taId;
    private int stockId;
    private double price;
    private int quantity;
    private int quantitySelled;
    private double amount;
    private Date date;

    public Holding(int taId, int stockId, double price, int quantity, double amount)
    {
        this.holdId = 0;
        this.taId = taId;
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
        this.amount = amount;
        this.quantitySelled = 0;
    }

    public void setHoldId(int holdId) 
    {
        this.holdId = holdId;
    }

    public int getQuantitySelled() 
    {
        return quantitySelled;
    }

    public void setQuantitySelled(int quantitySelled) 
    {
        this.quantitySelled = quantitySelled;
    }

    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }

    public void setDate(Date date) 
    {
        this.date = date;
    }

    public void setAmount(double amount) 
    {
        this.amount = amount;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getHoldId() 
    {
        return holdId;
    }

    public int getTaId() 
    {
        return taId;
    }

    public int getStockId() 
    {
        return stockId;
    }

    public double getPrice() 
    {
        return price;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public double getAmount() 
    {
        return amount;
    }

    public Date getDate() 
    {
        return date;
    }

   
    
}
