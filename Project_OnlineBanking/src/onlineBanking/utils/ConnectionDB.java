package onlineBanking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB 
{
	private static Connection con = null;
	private static final String url = "jdbc:postgresql://localhost:5432/onlinebanking";
	private static final String username = "postgres";
	private static final String password = "postgres";

	// Private constructor to prevent instantiation from outside
	private ConnectionDB() 
	{
		try 
		{
			Class.forName("org.postgresql.Driver");
		}

		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	// Static method to get the instance of the connection
	public static Connection getConnection() 
	{
		if (con == null) 
		{
			// Synchronize the creation of the connection to avoid race conditions
			synchronized (ConnectionDB.class) 
			{
				if (con == null) 
				{
					try 
					{
						con = DriverManager.getConnection(url, username, password);
					} 
					catch(SQLException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		return con;
	}

	// Method to close the connection
	public static void closeConnection() 
	{
		if (con != null) 
		{
			try 
			{
				con.close();
			} 
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
			finally 
			{
				con = null; // Set to null to indicate that the connection is closed
			}
		}
	}
}
