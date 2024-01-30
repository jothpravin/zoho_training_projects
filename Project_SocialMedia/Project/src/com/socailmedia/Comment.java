package socailmedia;

import java.sql.Timestamp;

class Comment
{
	private long commentID;
	private long postID;
	private long userID;
	private String content;
	private Timestamp timeStamp;
	
	public Comment(long postID, long userID, Timestamp timeStamp, String content)
	{
		this.commentID = 0;
		this.postID = postID;
		this.userID = userID;
		this.timeStamp = timeStamp;
		this.content = content;
	}
	
	public void setCommentID(long commentID)
	{
		this.commentID = commentID;
	}
	
	public long getCommentID()
	{
		return commentID;
	}
	
	public long getUserID()
	{
		return userID;
	}
	
	public long getPostID()
	{
		return postID;
	}
	
	public Timestamp getTimestamp()
	{
		return timeStamp;
	}

	public String getContent()
	{
		return content;
	}
}
