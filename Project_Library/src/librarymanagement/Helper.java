package librarymanagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper
{
    static Connection con = null;
    static Statement stm = null;   
    public Helper()
    {
        try
        {
            con = ConnectionDB.getConnection();
            stm = con.createStatement();
            createGenderEnum();
            createStatusEnum();
            createUsersTable();
            createBookCopyTable();
            createPublishersTable();
            createRackTable();
            createBookTable();
            createBookIssueTable();
            createBookReservationTable();
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

    private static void createGenderEnum() throws SQLException 
    {
        if (!typeExists("gender_enum")) 
        {
            String createQuery = "CREATE TYPE gender_enum AS ENUM('Male', 'Female', 'Other')";
            executeQuery(createQuery);
        }
    }
    
    
    private static boolean typeExists(String typeName) throws SQLException 
    {
        String checkQuery = "SELECT 1 FROM pg_type WHERE typname = '" + typeName + "'";
        
        try (ResultSet resultSet = stm.executeQuery(checkQuery)) 
        {
            return resultSet.next(); // Returns true if the type already exists
        }
    }
    

    private static void createStatusEnum() throws SQLException 
    {
        if(!typeExists("status_enum"))
        {
            String query = "CREATE TYPE Status_enum AS ENUM('Pending', 'Approved', 'Declined')";
            executeQuery(query);
        }
    }

    private static void createUsersTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "UserID SERIAL PRIMARY KEY," +
                "Username VARCHAR(50)," +
                "Email VARCHAR(50)," +
                "Password VARCHAR(50)," +
                "isAdmin BOOLEAN," +
                "Gender gender_enum," +
                "dob DATE" +
                ")";
        executeQuery(query);
    }

    private static void createPublishersTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Publishers (" +
                "PublisherID SERIAL PRIMARY KEY," +
                "PublisherName VARCHAR(100)" +
                ")";
        executeQuery(query);
    }

    private static void createRackTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Rack (" +
                "RackID SERIAL PRIMARY KEY," +
                "RackName VARCHAR(50)" +
                ")";
        executeQuery(query);
    }

    private static void createBookTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS Book (" +
                "BookID SERIAL PRIMARY KEY," +
                "BookName VARCHAR(100)," +
                "Author VARCHAR(100)," +
                "PublisherID INT REFERENCES Publishers(PublisherID)," +
                "Available INT" +
                ")";
        executeQuery(query);
    }

    private static void createBookCopyTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS BookCopy (" +
                "BC_ID SERIAL PRIMARY KEY," +
                "BookID INT REFERENCES Book(BookID)," +
                "RackID INT REFERENCES Rack(RackID)," +
                "Book_Token_No SERIAL" +
                ")";
        executeQuery(query);
    }

    private static void createBookIssueTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS BookIssue (" +
                "BI_ID SERIAL PRIMARY KEY," +
                "UserID INT REFERENCES Users(UserID)," +
                "BC_ID INT REFERENCES BookCopy(BC_ID)," +
                "DateOfIssue DATE," +
                "ReturnDate DATE," +
                "Fine DECIMAL" +
                ")";
        executeQuery(query);
    }

    private static void createBookReservationTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS BookReservation (" +
                "BR_ID SERIAL PRIMARY KEY," +
                "UserID INT REFERENCES Users(UserID)," +
                "BC_ID INT REFERENCES BookCopy(BC_ID)," +
                "DateOfReservation DATE DEFAULT current_date," +
                "Status Status_enum" +
                ")";
        executeQuery(query);
    }
}
