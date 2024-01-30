package com.stocktradingsystem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class TradingController 
{
    static Scanner in = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    private TradingDAO tradingDAO;
    public TradingController(TradingDAO tradingDAO)
    {
        this.tradingDAO = tradingDAO;
    }

    public void createTradingAccount()
    {
        if(!checkTradingAccount())
        {
            System.out.print("Enter the Aadhar number :");
            Long aadharNumber = in.nextLong();
            while(!Validator.isValidAadhar(aadharNumber))
            {
                System.out.print("Enter the Aadhar number :");
                aadharNumber = in.nextLong();
            }
            System.out.print("Enter the PAN card :");
            String pan = in.next();
            while(!Validator.isValidPan(pan))
            {
                System.out.print("Enter the PAN card :");
                pan = in.next();
            }
            System.out.print("Enter the Inital deposit amount: ");
            Double amount = in.nextDouble();
    
            TradingAccount tradingAccount = new TradingAccount(UserDAO.USERID, aadharNumber, pan, amount);
            tradingDAO.createTradingAccount(tradingAccount);
        }
        else
        {
            System.out.println("Already have a trading account!");
        }
    }

    private boolean checkTradingAccount()
    {
        return tradingDAO.isTradingAcount(UserDAO.USERID);
    }

    private void showStocks()
    {
        List<Stock> stocks = tradingDAO.getAllStocks();
        for(Stock stock: stocks)
        {
            System.out.println("===========================");
            System.out.println("Stock ID: "+stock.getStockId());
            System.out.println("Stock Name: "+stock.getStockName());
            System.out.println("Stock Price: "+ANSI_GREEN+stock.getPrice()+ANSI_RESET);
        }
        System.out.println("===========================");   
    }

    private boolean showHoldings()
    {
        List<Holding> holdings = tradingDAO.getAllHoldings();
        if(holdings.size() <= 0)
        {
            return false;
        }
        for(Holding holding: holdings)
        {
            double currentPrice = currentPrice(holding.getStockId());
            double buyPrice = holding.getPrice();
            double currentProfit = (currentPrice - buyPrice)*holding.getQuantity();
            System.out.println("===========================");
            System.out.println("Holding ID: "+holding.getHoldId());
            System.out.println("Stock ID: "+holding.getStockId());
            System.out.println("Stock Name: "+tradingDAO.getStock(holding.getStockId()).getStockName());
            System.out.println("Stock buy Price: "+buyPrice);
            System.out.println("Stock current Price :"+currentPrice);
            System.out.println("Quantity: "+holding.getQuantity());
            System.out.println("Total Amount: "+holding.getAmount());
            System.out.println("Buy Date: "+sdf.format(holding.getDate()));
            if(currentPrice >= 0)
            {
                System.out.println("Current profit :"+ANSI_GREEN+String.format("%.2f",currentProfit)+ANSI_RESET);
            }
            else
            {
                System.out.println("Current profit :"+ANSI_RED+String.format("%.2f",currentProfit)+ANSI_RESET);
            }
        }
        System.out.println("===========================");
        return true;
    }

    public void buyStock()
    {
        if(checkTradingAccount())
        {
            showStocks();
            System.out.print("Enter the stock id to buy or 0 to back: ");
            int stockid = in.nextInt();
            if(stockid == 0) return;
            System.out.print("Enter the stock quantity: ");
            int quantity = in.nextInt();
            Stock stock = tradingDAO.getStock(stockid);
            if(stock != null)
            {
                double totalAmount = stock.getPrice() * quantity;
                double currentAmount = getAmount();
                if(totalAmount <= currentAmount)
                {
                    Holding holding = new Holding(TradingDAO.TradingAccountID, stockid, stock.getPrice(), quantity, totalAmount);
                    Transaction transaction = new Transaction(Action.Buy, holding);
                    addHoldings(holding);
                    addTransaction(transaction);
                    updateAmount(currentAmount - totalAmount);
                }
                else
                {
                    System.out.println("Insufficient amount! :" +currentAmount);
                }
            }
            else
            {
                System.out.println("Invalid stock id!");
            }
        }
        else
        {
            System.out.println("Create Trading Account first!");
        }
    }

    public void sellStock() 
    {
        if(checkTradingAccount()) 
        {
            if(!showHoldings())
            {
                System.out.println("No holdings found!");
                return;
            }
            System.out.print("Enter the hold id to sell stock or 0 to back: ");
            int holdid = in.nextInt();
            if(holdid == 0)
            {
                return;
            }
            System.out.print("Enter the stock quantity: ");
            int quantity = in.nextInt();
    
            // Check if quantity is non-negative
            if(quantity < 0) 
            {
                System.out.println("Invalid quantity entered!");
                return;
            }
    
            Holding holding = tradingDAO.getHolding(holdid);
    
            if(holding.getQuantity() >= quantity) 
            {
                double currentStockAmount = currentPrice(holding.getStockId()) * quantity;
                double currentTradingAmount = getAmount();
                double userCurrentStockAmount = holding.getPrice() * quantity;
                double currentProfit = 0.0;
    
                if(currentStockAmount > userCurrentStockAmount) 
                { // profit
                    updateQuantity(holding.getQuantity() - quantity, holdid);
                    updateQuantitySelled(holding.getQuantitySelled() + quantity, holdid);
                    currentProfit = currentStockAmount + currentTradingAmount;
                    updateAmount(currentProfit);
                    updateStockAmount(holding.getAmount() - userCurrentStockAmount, holdid);
                    holding.setQuantity(quantity);
                    holding.setAmount(currentStockAmount);
                    holding.setPrice(currentPrice(holding.getStockId()));
                    Transaction transaction = new Transaction(Action.Sell, holding);
                    addTransaction(transaction);
                    double profit = currentStockAmount - userCurrentStockAmount;
                    System.out.println("Profit: "+ANSI_GREEN+String.format("%.2f",profit)+ANSI_RESET);
                } 
                else 
                { // loss
                    updateQuantity(holding.getQuantity() - quantity, holdid);
                    updateQuantitySelled(holding.getQuantitySelled() + quantity, holdid);
                    currentProfit = currentStockAmount - userCurrentStockAmount + currentTradingAmount;
                    updateAmount(currentProfit);
                    updateStockAmount(userCurrentStockAmount - holding.getAmount(), holdid);
                    holding.setQuantity(quantity);
                    holding.setAmount(currentStockAmount);
                    holding.setPrice(currentPrice(holding.getStockId()));
                    Transaction transaction = new Transaction(Action.Sell, holding);
                    addTransaction(transaction);
                    double loss = userCurrentStockAmount - currentStockAmount;
                    System.out.println("Loss: "+ANSI_RED+String.format("%.2f",loss)+ANSI_RESET);
                }
            }
            else 
            {
                System.out.println("Insufficient quantity!");
            }
        } 
        else 
        {
            System.out.println("Create Trading Account first!");
        }
    }

    public void portfolio()
    {
        List<Holding> holdings = tradingDAO.getAllHoldings();
        double totalProfit = totalProfit();
        double totalAmount = getAmount();
        if(totalProfit > 0)
        {
            System.out.println("--------------------------Portfolio-------------------------------");  
            System.out.println("Total Profit :"+ANSI_GREEN+String.format("%.2f",totalProfit)+ANSI_RESET+"         "+"Balance Amount :"+String.format("%.2f",totalAmount));
            System.out.println("---------------------------------------------------------");  
        }
        else
        {
            System.out.println("--------------------------Portfolio-------------------------------");  
            System.out.println("Total Profit :"+ANSI_RED+String.format("%.2f",totalProfit)+ANSI_RESET+"         "+"Balance Amount :"+String.format("%.2f",totalAmount));
            System.out.println("---------------------------------------------------------");  
        }

        for(Holding holding : holdings)
        {
            System.out.println("===================================");   
            double buyPrice = holding.getPrice();
            double currentPrice = currentPrice(holding.getStockId());
            System.out.println("Stock Name :"+tradingDAO.getStock(holding.getStockId()).getStockName());
            System.out.println("Stock Buy price :"+buyPrice);
            System.out.println("Stock Quantity :"+holding.getQuantity());
            System.out.println("Stock current Price :"+currentPrice);
            System.out.println("Stock Buyed Date :"+sdf.format(holding.getDate()));
            System.out.println("Total Amount :"+holding.getAmount());
            System.out.println("Current profit :"+String.format("%.2f",(currentPrice - buyPrice)*holding.getQuantity()));
        }
        System.out.println("===================================");   
    }

    private double totalProfit()
    {
        List<Holding> holdings = tradingDAO.getAllHoldings();
        double profit = 0.0;
        for(Holding holding : holdings)
        {
            double currentStockPrice = currentPrice(holding.getStockId());
            if(currentStockPrice > holding.getPrice())
            {
                double var = currentStockPrice - holding.getPrice();
                profit = profit + (var * holding.getQuantity());
            }
            else
            {
                double var = currentStockPrice - holding.getPrice();
                profit = profit + (var * holding.getQuantity());
            }
        }
        return profit;
    }

    private double getAmount()
    {
        return tradingDAO.getAmount();
    }

    private double currentPrice(int stockid)
    {
        return tradingDAO.getCurrentStockPrice(stockid);
    }

    private void addHoldings(Holding holding)
    {
        tradingDAO.addNewHolding(holding);
    }
    private void addTransaction(Transaction transaction)
    {
        tradingDAO.addNewTransaction(transaction);
    }

    private void updateAmount(double amount)
    {
        tradingDAO.updateAmount(amount);
    }

    private void updateQuantity(int quantity, int holdid)
    {
        tradingDAO.updateQuantity(quantity, holdid);
    }

    private void updateQuantitySelled(int quantitySelled, int holdid)
    {
        tradingDAO.updateQuantitySelled(quantitySelled, holdid); 
    } 

    private void updateStockAmount(double amount, int holdid)
    {
        tradingDAO.updateStockAmount(amount, holdid);
    }
}
