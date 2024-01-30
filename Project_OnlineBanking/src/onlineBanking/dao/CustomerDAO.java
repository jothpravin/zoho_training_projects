package onlineBanking.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import onlineBanking.model.Account;
import onlineBanking.model.Branch;
import onlineBanking.model.Customer;
import onlineBanking.utils.ConnectionDB;;

public class CustomerDAO 
{
    public static int CUSTOMER_ID;
	static Connection con = null;
	public CustomerDAO()
	{
		con = ConnectionDB.getConnection();
	}

    public Customer createCustomer(Customer customer) 
    {
        String query = "INSERT INTO customer (firstname, lastname, email, gender, date_of_birth, street_address, city, state_name, zip_code)"+
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            Date sqlDate = new Date(customer.getDob().getTime());
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setObject(4, customer.getGender(), Types.OTHER);
            ps.setDate(5, sqlDate);
            ps.setString(6, customer.getStreetAddress());
            ps.setString(7, customer.getCity());
            ps.setString(8, customer.getState());
            ps.setInt(9, customer.getPincode());

            int row = ps.executeUpdate();
            if(row > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    customer.setCustomerId(rs.getInt(1));
                    System.out.println("Successfully new customer account created");
                    return customer;
                }
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void createAccount(Account account)
    {
        String query = "INSERT INTO account (account_no, account_type, customer_id, branch_id, balance) VALUES"+
                        "(?, ?, ?, ?, ?)";

        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, account.getAccountNumber());
            ps.setObject(2, account.getAccountType(), Types.OTHER);
            ps.setInt(3, account.getCustomerId());
            ps.setInt(4, account.getBranchId());
            ps.setDouble(5, account.getAmount());
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully account created");
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Branch getBranch(String branchName)
    {
        String query = "SELECT * FROM branch WHERE branchname = ?";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, branchName);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                Branch branch = new Branch(rs.getString("branch_name"));
                branch.setBranchId(rs.getInt("branch_id"));
                branch.setIfscCode(rs.getString("ifsc_code"));
                return branch;
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createBranch(String branchName)
    {
        String query = "INSERT INTO branch (branch_name) VALUES (?)";
        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, branchName);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully branch created");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}