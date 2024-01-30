package onlineBanking.model;

import java.util.Date;

public class Transaction 
{
    private int transactionId;
    private int sender;
    private int reciever;
    private double amount;
    private TransactionType status;
    private Date transactionDate;

    public Transaction(int sender, int reciever, double amount, TransactionType status, Date transactionDate) 
    {
        this.sender = sender;
        this.reciever = reciever;
        this.amount = amount;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    public void setTransactionId(int transactionId) 
    {
        this.transactionId = transactionId;
    }

    public int getTransactionId() 
    {
        return transactionId;
    }

    public int getSender() 
    {
        return sender;
    }

    public int getReciever() 
    {
        return reciever;
    }

    public double getAmount() 
    {
        return amount;
    }

    public TransactionType getStatus() 
    {
        return status;
    }

    public Date getTransactionDate() 
    {
        return transactionDate;
    }
    
}
