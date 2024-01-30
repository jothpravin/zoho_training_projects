package socailmedia;

import java.sql.Timestamp;

class ChatMessage
{
	private long chatID;
	private long senderID;
	private long receiverID;
	private String content;
	private Timestamp timeStamp;
	
	public ChatMessage(long senderID, long receiverID, String content, Timestamp timeStamp)
	{
		this.chatID = 0;
		this.receiverID = receiverID;
		this.senderID = senderID;
		this.content = content;
		this.timeStamp = timeStamp;
	}
	
	public long getChatID()
	{
		return chatID;
	}
	
	public void setChatID(long chatID)
	{
		this.chatID = chatID;
	}
	
	public long getSenderID()
	{
		return senderID;
	}
	
	public long getRecieverID()
	{
		return receiverID;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}
	
}

