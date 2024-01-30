package com.stocktradingsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TradingDAO 
{
    static int TradingAccountID;
    Connection con = ConnectionDB.getConnection();

    public List<Stock> getAllStocks()
    {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stocks ORDER BY stockid";
        try(PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) 
        {
            while(rs.next())
            {
                Stock stock = new Stock(rs.getString("stockname"), rs.getDouble("stockprice"));
                stock.setStockId(rs.getInt("stockid"));
                stocks.add(stock);
            }
            return stocks;
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isTradingAcount(int userid)
    {
        String query = "SELECT * FROM tradingaccount WHERE userid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return true;
            }
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return false;
    }

    public void createTradingAccount(TradingAccount tradingAccount)
    {
        String query = "INSERT INTO tradingaccount (userid, aadhar_no, pan_no, amount) VALUES (?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, tradingAccount.getUserid());
            ps.setLong(2, tradingAccount.getAadhar_no());
            ps.setString(3, tradingAccount.getPan_no());
            ps.setDouble(4, tradingAccount.getAmount());
            int row = ps.executeUpdate();
            if(row > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    TradingAccountID = rs.getInt(1);
                    System.out.println("Successfully trading account created..!!");
                }
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Double getAmount()
    {
        String query = "SELECT amount from tradingaccount WHERE taid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, TradingAccountID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getDouble("amount");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return -1.0;
    }

    public void getTradingAccountId()
    {
        String query = "SELECT taid FROM tradingaccount WHERE userid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, UserDAO.USERID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                TradingAccountID = rs.getInt("taid");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public TradingAccount getTradingAccount()
    {
        String query = "SELECT * FROM tradingaccount where taid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, TradingAccountID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                TradingAccount tradingAccount = new TradingAccount(rs.getInt("userid"), rs.getLong("aadhar_no"), rs.getString("pan_no"), rs.getDouble("amount"));
                tradingAccount.setDemat_acc_no(rs.getLong("demat_acc_no"));
                tradingAccount.setTaId(TradingAccountID);
                return tradingAccount;
            }
        }
        catch(SQLException e)
        {

        }
        return null;
    }

    public Stock getStock(int stockid)
    {
        String query = "SELECT * FROM stocks WHERE stockid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, stockid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Stock stock = new Stock(rs.getString("stockname"), rs.getDouble("stockprice"));
                stock.setStockId(stockid);
                return stock;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getStockCount()
    {
        String query = "SELECT COUNT(*) FROM stocks";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getInt(1);
            }
        }
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return -1;
    }

    public void addNewHolding(Holding holdingStock)
    {
        String query = "INSERT INTO holdings (taid, stockid, price, quantity, amount) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, TradingAccountID);
            ps.setInt(2, holdingStock.getStockId());
            ps.setDouble(3, holdingStock.getPrice());
            ps.setInt(4, holdingStock.getQuantity());
            ps.setDouble(5, holdingStock.getAmount());
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully Stock Buyed.!!");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addNewTransaction(Transaction transaction)
    {
        String query = "INSERT INTO transactions (taid, action, stockid, price, quantity, amount) VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, TradingAccountID);
            ps.setObject(2, transaction.getAction(), Types.OTHER);
            ps.setInt(3, transaction.getHoldingStock().getStockId());
            ps.setDouble(4, transaction.getHoldingStock().getPrice());
            ps.setInt(5, transaction.getHoldingStock().getQuantity());
            ps.setDouble(6, transaction.getHoldingStock().getAmount());
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully Transaction Inserted..!!");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateAmount(double amount)
    {
        String query = "UPDATE tradingaccount SET amount = ? WHERE taid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setDouble(1, amount);
            ps.setInt(2, TradingAccountID);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully Amount Updated..!!");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateStockAmount(double amount, int holdid)
    {
        String query = "UPDATE holdings SET amount = ? WHERE holdingsid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setDouble(1, amount);
            ps.setInt(2, holdid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully Stock Amount Updated..!!");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Holding> getAllHoldings()
    {
        List<Holding> holdings = new ArrayList<>();
        String query = "SELECT * FROM holdings WHERE taid = ? ORDER BY taid";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, TradingAccountID);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                Holding holding = new Holding(rs.getInt("taid"), 
                                            rs.getInt("stockid"), 
                                            rs.getDouble("price"), 
                                            rs.getInt("quantity"), 
                                            rs.getDouble("amount")
                                            );
                holding.setHoldId(rs.getInt("holdingsid"));
                holding.setDate(rs.getDate("hold_date"));
                if(holding.getQuantity() > 0)
                {
                    holdings.add(holding);
                }
            }
            return holdings;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Holding getHolding(int holdid) 
    {
        String query = "SELECT * FROM holdings WHERE holdingsid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, holdid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Holding holding = new Holding(rs.getInt("taid"), 
                                            rs.getInt("stockid"), 
                                            rs.getDouble("price"), 
                                            rs.getInt("quantity"), 
                                            rs.getDouble("amount")
                                            );
                holding.setHoldId(rs.getInt("holdingsid"));
                holding.setDate(rs.getDate("hold_date"));
                holding.setQuantitySelled(rs.getInt("quantity_selled"));
                return holding;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public double getCurrentStockPrice(int stockid)
    {
        String query = "SELECT stockprice FROM stocks WHERE stockid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, stockid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getDouble("stockprice");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateQuantity(int quantity, int holdid)
    {
        String query = "UPDATE holdings set quantity = ? WHERE taid = ? AND holdingsid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, quantity);
            ps.setInt(2, TradingAccountID);
            ps.setInt(3, holdid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully updated quantity");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateQuantitySelled(int quantitySelled, int holdid)
    {
        String query = "UPDATE holdings set quantity_selled = ? WHERE taid = ? AND holdingsid = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, quantitySelled);
            ps.setInt(2, TradingAccountID);
            ps.setInt(3, holdid);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully updated quantity selled");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void closeConnection()
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


}
