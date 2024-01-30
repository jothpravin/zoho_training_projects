package com.stocktradingsystem;

public class TradingAccount 
{
    private int taId;
    private int userid;
    private long aadharNo;
    private String panNo;
    private long demat_acc_no;
    private double amount;

    public TradingAccount(int userid, long aadharNo, String panNo, double amount) 
    {
        this.userid = userid;
        this.aadharNo = aadharNo;
        this.panNo = panNo;
        this.amount = amount;
    }

    public void setTaId(int taId) 
    {
        this.taId = taId;
    }

    public void setAmount(double amount) 
    {
        this.amount = amount;
    }

    public void setDemat_acc_no(long demat_acc_no) 
    {
        this.demat_acc_no = demat_acc_no;
    }

    public int getTaId() 
    {
        return taId;
    }

    public int getUserid() 
    {
        return userid;
    }

    public long getAadhar_no() 
    {
        return aadharNo;
    }

    public String getPan_no() 
    {
        return panNo;
    }

    public long getDemat_acc_no() 
    {
        return demat_acc_no;
    }

    public double getAmount() 
    {
        return amount;
    }
}
