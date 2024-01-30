package socailmedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDAO 
{
    private static final String insert_message_query = "INSERT INTO chatmessages (senderid, receiverid, content, timestamp) VALUES (?, ?, ?, ?)";
    private static final String select_friends_details_query ="SELECT DISTINCT U.* " +
                                                                "FROM Users U " +
                                                                "JOIN Friends F ON U.userID = F.userID1 OR U.userID = F.userID2 " +
                                                                "WHERE U.userID <> ?";
    private static final String select_message_query = "SELECT * FROM chatmessages WHERE senderid = ? AND receiverid = ? OR senderid = ? AND receiverid = ? ORDER BY timestamp";
    private static final String select_online_query = "SELECT status FROM users WHERE userid = ?";

    public void insertNewMessage(ChatMessage cm)
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(insert_message_query, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setLong(1, cm.getSenderID());
            ps.setLong(2, cm.getRecieverID());
            ps.setString(3, cm.getContent());
            ps.setTimestamp(4, cm.getTimeStamp());

            int row = ps.executeUpdate();

            if(row > 0)
            {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    cm.setChatID(rs.getLong(1));
                    System.out.println("Successfully message inserted into db");
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

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(select_friends_details_query))
        {
            ps.setLong(1, UserDAO.USER_ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                User user = new User(rs.getString("username"),
                                    rs.getString("email"), 
                                    rs.getString("password"), 
                                    rs.getDate("dob"), 
                                    rs.getString("bio"), 
                                    Gender.valueOf(rs.getString("gender")));
                user.setUserID(rs.getLong("userid"));
                users.add(user);
            }
            return users;
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

    public List<ChatMessage> getAllMessages(int friendId, long userid)
    {
        List<ChatMessage> messages = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(select_message_query))
        {
            ps.setInt(1, friendId);
            ps.setLong(2, userid);
            ps.setLong(3, userid);
            ps.setInt(4, friendId);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                ChatMessage chatMessage = new ChatMessage(rs.getLong("senderid"), 
                                                        rs.getLong("receiverid"), 
                                                        rs.getString("content"), 
                                                        rs.getTimestamp("timestamp")
                                                        );
                messages.add(chatMessage);
            }
            return messages;
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

    public boolean isUserOnline(long userID) 
    {
        Connection con = ConnectionDB.getConnection();
        try(PreparedStatement ps = con.prepareStatement(select_online_query))
        {
            ps.setLong(1, userID);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                return rs.getString("status").equals("online");
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
        return false;
    }

}
