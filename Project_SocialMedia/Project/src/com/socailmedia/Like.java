package socailmedia;

import java.sql.Timestamp;

class Like
{
	private long likeID;
	private long postID;
	private long userID;
	private Timestamp timeStamp;
	
	public Like(long postID, long userID, Timestamp timeStamp)
	{
		this.likeID = 0;
		this.postID = postID;
		this.userID = userID;
		this.timeStamp = timeStamp;
	}
	
	public void setLikeID(long likeID)
	{
		this.likeID = likeID;
	}
	
	public long getLikeID()
	{
		return likeID;
	}
	
	public long userID()
	{
		return userID;
	}
	
	public long postID()
	{
		return postID;
	}
	
	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}
}
