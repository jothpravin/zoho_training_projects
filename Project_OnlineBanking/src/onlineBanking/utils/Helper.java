package onlineBanking.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper 
{

    private Connection con = null;
    private static Statement stm = null;

    public Helper() 
    {
        try 
        {
            con = ConnectionDB.getConnection();
            stm = con.createStatement();
            createSequence("branch_branch_id_seq", 143);
            createSequence("customer_cif_id", 21345931);
            createEnumTypes();
            createCustomerTable();
            createBranchTable();
            createAccountTable();
            createTransactionTable();
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            ConnectionDB.closeConnection();
        }
    }

    private static void executeQuery(String query) throws SQLException 
    {
        stm.executeUpdate(query);
    }

    private static void createEnumTypes() throws SQLException 
    {
        createEnumType("gender_enum", "'Male', 'Female'");
        createEnumType("status_enum", "'Credit', 'Debit'");
        createEnumType("account_enum", "'Savings', 'Current'");
    }

    private static void createEnumType(String typeName, String values) throws SQLException 
    {
        if(!typeExists(typeName)) 
        {
            String createQuery = "CREATE TYPE " + typeName + " AS ENUM (" + values + ")";
            executeQuery(createQuery);
        }
    }

    private static boolean typeExists(String typeName) throws SQLException 
    {
        String checkQuery = "SELECT 1 FROM pg_type WHERE typname = '" + typeName + "'";
        try(ResultSet resultSet = stm.executeQuery(checkQuery)) 
        {
            return resultSet.next();
        }
    }

    private static void createCustomerTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Customer (" +
                "customer_id SERIAL PRIMARY KEY," +
                "first_name VARCHAR(50)," +
                "last_name VARCHAR(50)," +
                "email VARCHAR(150)," +
                "password VARCHAR(150)," +
                "mobile INT," +
                "gender gender_enum," +
                "date_of_birth DATE," +
                "street_address VARCHAR(255)," +
                "city VARCHAR(50)," +
                "state_name VARCHAR(50)," +
                "zip_code INT," +
                "cif_id INT DEFAULT nextval('customer_cif_id')" +
                ")";
        executeQuery(query);
    }

    private static void createBranchTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Branch (" +
                "branch_id SERIAL PRIMARY KEY," +
                "branch_name VARCHAR(255) NOT NULL," +
                "ifsc_code VARCHAR(255) DEFAULT 'SBI' || '00000' || nextval('branch_branch_id_seq')" +
                ")";
        executeQuery(query);
    }

    private static void createAccountTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Account (" +
                "account_id SERIAL PRIMARY KEY," +
                "account_no INT," +
                "account_type account_enum," +
                "customer_id INT REFERENCES Customer(customer_id)," +
                "branch_id INT REFERENCES Branch(branch_id)," +
                "balance DECIMAL" +
                ")";
        executeQuery(query);
    }

    private static void createTransactionTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Transaction (" +
                "transaction_id SERIAL PRIMARY KEY," +
                "from_account_id INT REFERENCES Account(account_id)," +
                "to_account_id INT REFERENCES Account(account_id)," +
                "amount DECIMAL NOT NULL," +
                "transaction_type VARCHAR(50) NOT NULL," +
                "transaction_date DATE DEFAULT CURRENT_DATE," +
                "t_status status_enum," +
                "current_balance DECIMAL" +
                ")";
        executeQuery(query);
    }

    private static void createSequence(String sequenceName, int startWith) throws SQLException 
    {
        String query = "CREATE SEQUENCE IF NOT EXISTS " + sequenceName + " START WITH " + startWith;
        executeQuery(query);
    }
    
}
