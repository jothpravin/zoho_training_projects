package com.stocktradingsystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserDAO 
{
    static int USERID;
    Connection con = ConnectionDB.getConnection();

    public void createNewUser(User user)
    {
        String user_insert_query = "INSERT INTO users (fname, lname, email, password, mobile_no, dob, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = con.prepareStatement(user_insert_query)) 
        {
            Date sqlDate = new Date(user.getDob().getTime());
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setLong(5, user.getMobile_no());
            ps.setDate(6, sqlDate);
            ps.setObject(7, user.getGender(), Types.OTHER);
            int row = ps.executeUpdate();
            if(row > 0)
            {
                System.out.println("Successfully created new user");
            }
        }
        catch(SQLException e) 
        {
           e.printStackTrace();
        }
    }

    public int login(String email, String password)
    {
        String user_login_query = "SELECT userid, password FROM users WHERE email = ?";

        try(PreparedStatement ps = con.prepareStatement(user_login_query))
        {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                if(rs.getString("password").equals(password))
                {
                    USERID = rs.getInt("userid");
                    return USERID;
                }
            }
            rs.close();
        }
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return -1;
    }

    public User getUser(int userid)
    {
        String query = "SELECT * FROM users WHERE userid = ?";
        try(PreparedStatement ps = con.prepareStatement(query)) 
        {
            ps.setInt(1, userid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                User user = new User(rs.getString("fname")
                                    , rs.getString("lname")
                                    , rs.getString("email")
                                    , rs.getString("password")
                                    , rs.getLong("mobile_no")
                                    , Gender.valueOf(rs.getString("gender"))
                                    , rs.getDate("dob")
                                    );
                user.setUserid(rs.getInt("userid"));
                return user;
            }
        } 
        catch(SQLException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
}
