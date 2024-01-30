package librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB 
{
	private static Connection con = null;
	private static final String url = "jdbc:postgresql://localhost:5432/librarymanagement";
	private static final String username = "postgres";
	private static final String password = "postgres";

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

	public static Connection getConnection() 
	{
		if (con == null) 
		{
			synchronized(ConnectionDB.class) 
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
				con = null; 
			}
		}
	}
}