package socailmedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;

class PostDAO
{
    private static final String post_insert_query = "INSERT INTO posts (userid, content, timestamp, views) VALUES (?, ?, ?, ?)";
    private static final String post_delete_query = "DELETE FROM posts WHERE postid = ?";
    private static final String post_summary_query = "SELECT postid, content FROM posts WHERE userid = ?";
	private static final String post_feed_query =	"WITH FriendPosts AS (" +
													"    SELECT P.*" +
													"    FROM Posts P" +
													"    JOIN Friends F ON P.userID = F.userID2" +
													"    WHERE F.userID1 = ? AND F.status = 'accepted'" +
													"    UNION" +
													"    SELECT P.*" +
													"    FROM Posts P" +
													"    JOIN Friends F ON P.userID = F.userID1" +
													"    WHERE F.userID2 = ? AND F.status = 'accepted'" +
													")" +
													"SELECT FP.*, U.username AS author_username" +
													" FROM FriendPosts FP" +
													" JOIN Users U ON FP.userID = U.userID" +
													" ORDER BY FP.timestamp DESC;";

	private static final String select_comment_query = "SELECT * FROM comments WHERE postid = ?";
	private static final String select_liked_query = "SELECT * FROM likes WHERE userid = ? AND postid = ?";
	private static final String select_like_query = "SELECT COUNT(*) AS like_count FROM Likes WHERE postID = ?";
    private static final String comment_insert_query = "INSERT INTO comments (postid, userid, content, timestamp) VALUES (?, ?, ?, ?)";
	private static final String query = "INSERT INTO Likes (userID, postID, timestamp) VALUES (?, ?, CURRENT_TIMESTAMP)";

	public void createPost(Post p)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(post_insert_query, Statement.RETURN_GENERATED_KEYS);)
		{
			ps.setLong(1, p.getUserID());
			ps.setString(2, p.getContent());
			ps.setTimestamp(3, p.getTimestamp());
			ps.setLong(4, p.getViews());
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					p.setPostID(rs.getInt(1));
					System.out.println("Successfully Posted");
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

	public void deletePost(long postid)
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(post_delete_query);)
		{
			ps.setLong(1, postid);
			int row = ps.executeUpdate();
			if(row > 0)
			{
				System.out.println("Successfully Deleted");
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

	public List<String> showPostSum()
	{
		List<String> posts = new ArrayList<>();

		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(post_summary_query);)
		{
			ps.setLong(1,UserDAO.USER_ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
					String post = "Post ID: "+rs.getLong(1)+" Content :"+
									rs.getString(2);
									
				posts.add(post);
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
		return posts;
	}

	public List<Post> showAllFeeds()
	{
		List<Post> posts = new ArrayList<>();
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(post_feed_query);)
		{
			ps.setLong(1, UserDAO.USER_ID);
			ps.setLong(2, UserDAO.USER_ID);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Post post = new Post(
									rs.getLong("userid"), 
									rs.getString("content"), 
									rs.getString("author_username"), 
									rs.getTimestamp("timestamp")
									);					
									post.setPostID(rs.getLong("postid"));
				posts.add(post);
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
		return posts;
	}

    public int getLikesCount(long postID) 
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(select_like_query);)
		{
			ps.setLong(1, postID);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				return rs.getInt("like_count");
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

    public List<Comment> getCommentsForPost(long postID) 
	{
		List<Comment> comments = new ArrayList<>();
        Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(select_comment_query);)
		{
			ps.setLong(1, postID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Comment comment = new Comment(rs.getLong("postid")
											,rs.getLong("userid")
											, rs.getTimestamp("timestamp")
											,rs.getString("content"));
				comment.setCommentID(rs.getLong("commentid"));
				comments.add(comment);
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
        return comments;
    }

    public void addComment(Comment comment) 
	{
		Connection con = ConnectionDB.getConnection();
		try(PreparedStatement ps = con.prepareStatement(comment_insert_query, Statement.RETURN_GENERATED_KEYS);)
		{
			ps.setLong(1, comment.getPostID());
			ps.setLong(2, comment.getUserID());
			ps.setString(3, comment.getContent());
			ps.setTimestamp(4, comment.getTimestamp());
			
			int row = ps.executeUpdate();
			if(row > 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
				if(rs.next())
				{
					comment.setCommentID(rs.getInt(1));
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

    public void addLike(long userID, long postID) 
	{
        Connection con = ConnectionDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(query)) 
		{
            ps.setLong(1, userID);
            ps.setLong(2, postID);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0) 
			{
                System.out.println("Like added successfully!");
            } 
			else 
			{
                System.out.println("Failed to add like.");
            }
        } 
		catch(SQLException e) 
		{
            System.out.println("You already Liked the Post");
        }
		finally 
		{
            ConnectionDB.closeConnection();
        }
    }

	public boolean isLikedPost(long us_id, long ps_id)
	{
		Connection con = ConnectionDB.getConnection();

        try (PreparedStatement ps = con.prepareStatement(select_liked_query)) 
		{
            ps.setLong(1, us_id);
            ps.setLong(2, ps_id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) 
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
            ConnectionDB.closeConnection();
        }
		return false;
	}
}