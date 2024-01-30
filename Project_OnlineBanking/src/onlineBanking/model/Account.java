package onlineBanking.model;

public class Account 
{
    private int accountId;
    private long accountNumber;
    private AccountType accountType;
    private double amount;
    private int customerId;
    private int branchId;

    public Account(long accountNumber, AccountType accountType, double amount, int customerId, int branchId) 
    {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.amount = amount;
        this.customerId = customerId;
        this.branchId = branchId;
    }

    public void setAccountId(int accountId) 
    {
        this.accountId = accountId;
    }

    public int getAccountId() 
    {
        return accountId;
    }

    public long getAccountNumber() 
    {
        return accountNumber;
    }

    public AccountType getAccountType() 
    {
        return accountType;
    }

    public double getAmount() 
    {
        return amount;
    }

    public int getCustomerId() 
    {
        return customerId;
    }

    public int getBranchId() 
    {
        return branchId;
    }
}
