package socailmedia;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

class UserDAO
{
	static long USER_ID;
	
	private static final String user_insert_query = "INSERT INTO users (username, password, email, dob, bio, gender) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String user_select_query = "SELECT * FROM users WHERE userid = ?";
	private static final String select_post_count_query = "SELECT COUNT(*) AS post_count FROM posts WHERE userid = ?";
	private static final String user_online_status = "UPDATE users SET status = 'online' WHERE email = ?";
	private static final String login_query = "SELECT password, userid FROM users WHERE email = ?";
	private static final String logout_query = "UPDATE users SET status = 'offline' WHERE userid = ?";
	private static final String slect_author_query = "SELECT username FROM users WHERE userid = ?";
	private static final String slect_users_query = "SELECT DISTINCT U.* "+
													"FROM Users U "+
													"WHERE NOT EXISTS ( "+
														"SELECT 1 "+
														"FROM Friends F "+
														"WHERE U.userID IN (F.userID1, F.userID2)"+
													")";
	private static final String connect_firends_query = "INSERT INTO friends (userid1, userid2, status) values (?, ?, ?)";
	private static final String update_following_query = "UPDATE users SET following = following + 1 WHERE usersid = ?";
	private static final String update_followers_query = "UPDATE users SET followers = followers + 1 WHERE usersid = ?";
	
	public void addUser(User u)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(user_insert_query, Statement.RETURN_GENERATED_KEYS);)
		{
			Date sqlDate = new Date(u.getDob().getTime());
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getEmail());
			ps.setDate(4, sqlDate);
			ps.setString(5, u.getBio());
			ps.setObject(6, u.getGender(), Types.OTHER);
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					u.setUserID(rs.getInt(1));
					System.out.println("Successfully inserted into User table");
				}
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
	}
	
	public void statusOnline(String email)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(user_online_status);)
		{
			ps.setString(1, email);
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("User is back to Online");
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
	}
	
	public long loginCredential(String emailID, String password)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(login_query);)
		{
			ps.setString(1, emailID);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getString("password").equals(password))
				{
					statusOnline(emailID);
					USER_ID = rs.getLong("userid");
					return rs.getLong("userid");
				}
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
		return -1;
	}

	public boolean logout()
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(logout_query);)
		{
			ps.setLong(1, USER_ID);
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				return true;
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
		return false;
	}

	public String getAuthor(long userId)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(slect_author_query);)
		{
			ps.setLong(1, userId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getString(1);
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
		return null;
	}

	public List<User> getUserInfo()
	{
		List<User> users = new ArrayList<>();

		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(slect_users_query);)
		{	
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				User user = new User(rs.getString("username"), 
										rs.getString("password"), 
										rs.getString("email"), 
										rs.getDate("dob"), 
										rs.getString("bio"), 
										Gender.valueOf(rs.getString("gender"))
										);
				user.setUserID(rs.getLong("userid"));
				users.add(user);
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
		return users;
	}

    public void connectFriends(long uSER_ID2, long id) 
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(connect_firends_query, Statement.RETURN_GENERATED_KEYS);)
		{
			ps.setLong(1, uSER_ID2);
			ps.setLong(2, id);
			ps.setString(3, "accepted");
			
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					System.out.println("Conected with "+getAuthor(id));
				}
			}
			updateFollwers(id);
			updateFollowing(uSER_ID2);
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
    }

	private void updateFollwers(long id)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(update_followers_query);)
		{
			ps.setLong(1, id);
			ps.executeUpdate();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
	}

	private void updateFollowing(long id)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(update_following_query);)
		{
			ps.setLong(1, id);
			ps.executeUpdate();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
	}

	public List<User> getFullUserInfo(long userid)
	{
		List<User> users = new ArrayList<>();
		
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(user_select_query);)
		{	
			ps.setLong(1, userid);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				User user = new User(rs.getString("username"), 
										rs.getString("password"), 
										rs.getString("email"), 
										rs.getDate("dob"), 
										rs.getString("bio"), 
										Gender.valueOf(rs.getString("gender"))
										);
				user.setUserID(rs.getLong("userid"));
				user.setFollowers(rs.getLong("followers"));
				user.setFollowing(rs.getLong("following"));
				user.setJoinedDate(rs.getDate("joined"));
				user.setStatus(Status.valueOf(rs.getString("status")));
				users.add(user);
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}

		return users;
	}

	public long getUserPostCount(long id)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(select_post_count_query);)
		{
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				return rs.getInt("post_count");
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionDB.getConnection();
		}
		return -1;
	}
}
