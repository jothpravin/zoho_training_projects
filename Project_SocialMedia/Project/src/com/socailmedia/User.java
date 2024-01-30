package socailmedia;

import java.util.Date;

class User
{
	private long userID;
	private String userName;
	private String password; 
	private String email;
	private Date dob;
	private String bio;
	private Status status;
	private Gender gender;
	private long following;
	private long followers;
	private Date joined;
	
	public User(String userName, String password, String email, Date dob, String bio, Gender gender)
	{
		this.userID = 0;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.bio = bio;
		this.gender = gender;
		this.followers = 0;
		this.following = 0;
		this.joined = null;
	}
	
	public long getUserID() 
	{
		return userID;
	}

	public void setUserID(long userID) 
	{
		this.userID = userID;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public void setFollowers(long followers)
	{
		this.followers = followers;
	}

	public void setFollowing(long following)
	{
		this.following = following;
	}

	public void setJoinedDate(Date joined)
	{
		this.joined = joined;
	}

	public String getUserName() 
	{
		return userName;
	}
	
	public String getPassword() 
	{
		return password;
	}

	public String getEmail() 
	{
		return email;
	}

	public Date getDob() 
	{
		return dob;
	}

	public String getBio() 
	{
		return bio;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public Gender getGender()
	{
		return gender;
	}

	public long getFollowing()
	{
		return following;
	}

	public long getFollowers()
	{
		return followers;
	}

	public Date getJoined()
	{
		return joined;
	}
}


enum Status
{
	online,
	offline
}

enum Gender
{
	male,
	female
}
