package ecommerence.utils;

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
            createEnums();
            createCustomersTable();
            createMerchantTable();
            createCategoryTable();
            createProductsTable();
            createRatingsTable();
            createAddressTable();
            createCartTable();
            createOrderTable();
            insertValuesQuery();
            updateDeliveryStatus();
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                stm.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
            ConnectionDB.closeConnection();
        }
    }

    private static void executeQuery(String query) throws SQLException 
    {
        stm.executeUpdate(query);
    }

    private static void createEnums() throws SQLException 
    {
        createEnumType("product_status", "'Active', 'Inactive'");
        createEnumType("delivery_status", "'YetToDeliver', 'Delivered', 'Cancelled', 'Returned'");
        createEnumType("gender_enum", "'Male', 'Female'");
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

    private static void createCustomersTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Customers (" +
                "customerid SERIAL PRIMARY KEY," +
                "firstname VARCHAR," +
                "lastname VARCHAR," +
                "gender gender_enum," +
                "email VARCHAR," +
                "password VARCHAR," +
                "mobile BIGINT," +
                "wallet BIGINT" +
                ")";
        executeQuery(query);
    }

    private static void createMerchantTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Merchant (" +
                "merchantid SERIAL PRIMARY KEY," +
                "firstname VARCHAR," +
                "lastname VARCHAR," +
                "email VARCHAR," +
                "password VARCHAR," +
                "mobile_no BIGINT," +
                "companyname VARCHAR," +
                "address VARCHAR," +
                "gst_no VARCHAR," +
                "pan_no VARCHAR," +
                "amount BIGINT" +
                ")";
        executeQuery(query);
    }

    private static void createProductsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Products (" +
                "productid SERIAL PRIMARY KEY," +
                "productname VARCHAR," +
                "productdesc VARCHAR," +
                "price INTEGER," +
                "categoryid INTEGER REFERENCES category(categoryid)," +
                "merchantid INTEGER REFERENCES Merchant(merchantid)," +
                "available INTEGER," +
                "ratings INTEGER," +
                "listdate DATE," +
                "p_status product_status DEFAULT 'Active'" +
                ")";
        executeQuery(query);
    }

    private static void createCategoryTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS category (" +
                "categoryid SERIAL PRIMARY KEY," +
                "category_name VARCHAR" +
                ")";
        executeQuery(query);
    }

    private static void createRatingsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Ratings (" +
                "ratingid SERIAL PRIMARY KEY," +
                "rating INTEGER," +
                "productid INTEGER REFERENCES Products(productid)," +
                "customerid INTEGER REFERENCES Customers(customerid)" +
                ")";
        executeQuery(query);
    }

    private static void createAddressTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Address (" +
                "Addressid SERIAL PRIMARY KEY," +
                "Address VARCHAR," +
                "city VARCHAR," +
                "state_name VARCHAR," +
                "pincode INTEGER," +
                "customerid INTEGER REFERENCES Customers(customerid)" +
                ")";
        executeQuery(query);
    }

    private static void createCartTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Cart (" +
                "cartid SERIAL PRIMARY KEY," +
                "customerid INTEGER REFERENCES Customers(customerid)," +
                "productid INTEGER REFERENCES Products(productid)," +
                "quantity INTEGER," +
                "amount DOUBLE PRECISION," +
                "cart_date DATE," +
                "isbuyed BOOLEAN DEFAULT 'false'" +
                ")";
        executeQuery(query);
    }

    private static void createOrderTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS OrderTable (" +
                "orderid SERIAL PRIMARY KEY," +
                "customerid INTEGER REFERENCES Customers(customerid)," +
                "productid INTEGER REFERENCES Products(productid)," +
                "merchantid INTEGER REFERENCES Merchant(merchantid)," +
                "addressid INTEGER REFERENCES Address(addressid)," +
                "quantity INTEGER," +
                "buy_price INTEGER," +
                "amount INTEGER," +
                "order_date DATE DEFAULT CURRENT_DATE," +
                "delivery_date DATE DEFAULT CURRENT_DATE + 3," +
                "d_status delivery_status DEFAULT 'YetToDeliver'" +
                ")";
        executeQuery(query);
    }

    private static int getCategoryCount() throws SQLException
    {
        String query = "SELECT COUNT(*) FROM category";
        ResultSet rs = stm.executeQuery(query);
        if(rs.next())
        {
            return rs.getInt(1);
        }
        return -1;
    }

    private static void insertValuesQuery() throws SQLException
    {
        if(getCategoryCount() == 0)
        {
            insertCategories();
        }
    }

    private static void insertCategories() throws SQLException 
    {
        // Insert values into the category table
        String query = "INSERT INTO category (category_name) VALUES " +
                "('Electronics and Gadgets')," +
                "('Fashion and Apparel')," +
                "('Home and Furniture')," +
                "('Beauty and Personal Care')," +
                "('Books and Stationery')," +
                "('Health and Fitness')," +
                "('Toys and Games')," +
                "('Groceries and Food')," +
                "('Automotive and Tools')," +
                "('Sports and Outdoor')";
        executeQuery(query);
    }

    private static void updateDeliveryStatus() throws SQLException
    {
        String query = "UPDATE ordertable SET d_status = 'Delivered' WHERE delivery_date = CURRENT_DATE AND d_status = 'YetToDeliver'";
        executeQuery(query);
    }
}
