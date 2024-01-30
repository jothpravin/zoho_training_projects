package librarymanagement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class UserDAO 
{
    static int USERID;
    private static final String user_signup_query = "INSERT INTO users (username, email, password, isadmin, gender, dob) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String user_login_query = "SELECT password, userid from users WHERE email = ? AND isAdmin = 'false'";
    private static final String admin_login_query = "SELECT password, userid from users WHERE email = ? AND isAdmin = 'true'";
    private static final String user_name_select_query = "SELECT username FROM users WHERE userid = ?";

    public void createNewUser(User user)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(user_signup_query, Statement.RETURN_GENERATED_KEYS))
        {
			Date sqlDate = new Date(user.getDob().getTime());
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmailID());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.getIsAdmin());
            ps.setObject(5, user.getGender(), Types.OTHER);
            ps.setDate(6, sqlDate);

            int row = ps.executeUpdate();

            if(row > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    user.setUserID(rs.getInt(1));
                    System.out.println("Sccuessfully inserted into users table");
                }
            }
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

    public int loginUser(String email, String pass) 
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(user_login_query)) 
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("password").equals(pass))
                {
                    USERID = rs.getInt("userid");
                    return USERID;
                }
            }
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return -1;
    }

    public int loginAdmin(String email, String pass) 
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(admin_login_query)) 
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("password").equals(pass))
                {
                    USERID = rs.getInt("userid");
                    return USERID;
                }
            }
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return -1;
    }

    public String getUserNameDB()
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(user_name_select_query))
        {
            ps.setInt(1, USERID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getString("username");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionDB.closeConnection();
        }
        return null;
    }

}
