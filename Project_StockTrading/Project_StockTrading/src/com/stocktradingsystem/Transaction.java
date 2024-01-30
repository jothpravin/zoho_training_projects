package com.stocktradingsystem;


public class Transaction 
{
    private int transactionId;
    private Action action;
    private Holding holdingStock;

    public Transaction(Action action, Holding holdingStock) 
    {
        this.transactionId = 0;
        this.action = action;
        this.holdingStock = holdingStock;
    }

    public Action getAction() 
    {
        return action;
    }

    public Holding getHoldingStock() 
    {
        return holdingStock;
    }

    public void setTransactionId(int transactionId) 
    {
        this.transactionId = transactionId;
    }

    public int getTransactionId() 
    {
        return transactionId;
    }
}

enum Action
{
    Buy,
    Sell
}