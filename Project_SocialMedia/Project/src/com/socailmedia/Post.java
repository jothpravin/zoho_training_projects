package socailmedia;

import java.sql.Timestamp;

class Post
{
	private long postID;
	private long userID;
	private String content;
	private String authorName;
	private Timestamp timeStamp;
	private long views;
	
	public Post(long userID, String content, String authorName, Timestamp timeStamp)
	{
		this.postID = 0;
		this.userID = userID;
		this.content = content;
		this.authorName = authorName;
		this.timeStamp = timeStamp;
		this.views = 0; 
	}
	
	public long getPostID()
	{
		return postID;
	}

	public void setPostID(long postID) 
	{
		this.postID = postID;
	}

	public long getUserID() 
	{
		return userID;
	}

	public String getContent() 
	{
		return content;
	}
	
	public Timestamp getTimestamp() 
	{
		return timeStamp;
	}
	
	public long getViews() 
	{
		return views;
	}

	public String getAuthorName()
	{
		return authorName;
	}
}
