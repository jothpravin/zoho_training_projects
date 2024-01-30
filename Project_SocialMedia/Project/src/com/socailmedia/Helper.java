package socailmedia;

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
            createEnumTypes();
            createUsersTable();
            createPostsTable();
            createLikesTable();
            createCommentsTable();
            createFriendsTable();
            createChatMessagesTable();
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
        createEnumType("user_status", "'online', 'offline'");
        createEnumType("gender", "'male', 'female'");
    }

    private static void createEnumType(String typeName, String values) throws SQLException 
    {
        if (!typeExists(typeName)) 
        {
            String createQuery = "CREATE TYPE " + typeName + " AS ENUM (" + values + ")";
            executeQuery(createQuery);
        }
    }

    private static boolean typeExists(String typeName) throws SQLException 
    {
        String checkQuery = "SELECT 1 FROM pg_type WHERE typname = '" + typeName + "'";
        try (ResultSet resultSet = stm.executeQuery(checkQuery)) 
        {
            return resultSet.next();
        }
    }

    private static void createUsersTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS users (" +
                "userid SERIAL PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL UNIQUE," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL UNIQUE," +
                "dob DATE," +
                "bio TEXT," +
                "status user_status DEFAULT 'offline'::user_status," +
                "gender gender," +
                "followers BIGINT," +
                "following BIGINT," +
                "joined DATE NOT NULL DEFAULT CURRENT_DATE" +
                ")";
        executeQuery(query);
    }

    private static void createPostsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS posts (" +
                "postid SERIAL PRIMARY KEY," +
                "userid INTEGER," +
                "content TEXT," +
                "timestamp TIMESTAMP WITHOUT TIME ZONE," +
                "views INTEGER DEFAULT 0," +
                "CONSTRAINT posts_userid_fkey FOREIGN KEY (userid) REFERENCES users(userid)" +
                ")";
        executeQuery(query);
    }

    private static void createLikesTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS likes (" +
                "likeid SERIAL," +
                "postid INTEGER NOT NULL," +
                "userid INTEGER NOT NULL," +
                "timestamp TIMESTAMP WITHOUT TIME ZONE," +
                "CONSTRAINT likes_id UNIQUE (likeid)," +
                "CONSTRAINT likes_new_pkey PRIMARY KEY (postid, userid)," +
                "CONSTRAINT likes_postid_fkey FOREIGN KEY (postid) REFERENCES posts(postid)," +
                "CONSTRAINT likes_userid_fkey FOREIGN KEY (userid) REFERENCES users(userid)" +
                ")";
        executeQuery(query);
    }

    private static void createCommentsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS comments (" +
                "commentid SERIAL PRIMARY KEY," +
                "postid INTEGER," +
                "userid INTEGER," +
                "content TEXT," +
                "timestamp TIMESTAMP WITHOUT TIME ZONE," +
                "CONSTRAINT comments_postid_fkey FOREIGN KEY (postid) REFERENCES posts(postid)," +
                "CONSTRAINT comments_userid_fkey FOREIGN KEY (userid) REFERENCES users(userid)" +
                ")";
        executeQuery(query);
    }

    private static void createFriendsTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS friends (" +
                "friendshipid SERIAL PRIMARY KEY," +
                "userid1 INTEGER," +
                "userid2 INTEGER," +
                "status VARCHAR(10)," +
                "CONSTRAINT friends_status_check CHECK (status IN ('pending', 'accepted', 'rejected'))," +
                "CONSTRAINT friends_userid1_fkey FOREIGN KEY (userid1) REFERENCES users(userid)," +
                "CONSTRAINT friends_userid2_fkey FOREIGN KEY (userid2) REFERENCES users(userid)" +
                ")";
        executeQuery(query);
    }

    private static void createChatMessagesTable() throws SQLException 
    {
        String query = "CREATE TABLE IF NOT EXISTS chatmessages (" +
                "messageid SERIAL PRIMARY KEY," +
                "senderid INTEGER," +
                "receiverid INTEGER," +
                "content TEXT," +
                "timestamp TIMESTAMP WITHOUT TIME ZONE," +
                "CONSTRAINT chatmessages_senderid_fkey FOREIGN KEY (senderid) REFERENCES users(userid)," +
                "CONSTRAINT chatmessages_receiverid_fkey FOREIGN KEY (receiverid) REFERENCES users(userid)" +
                ")";
        executeQuery(query);
    }
}

