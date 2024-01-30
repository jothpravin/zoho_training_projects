package librarymanagement;

import java.util.Date;

class User
{
    private int userID;
    private String userName;
    private String emailID;
    private String password;
    private boolean isAdmin;
    private Gender gender;
    private Date dob;

    public User(String userName, String emailID, String password, boolean isAdmin, Gender gender, Date dob)
    {
        this.userID = 0;
        this.userName = userName;
        this.emailID = emailID;
        this.password = password;
        this.gender = gender;
        this.isAdmin = isAdmin;
        this.dob = dob;
    }

    public void setUserID(int userID) 
    {
        this.userID = userID;
    }

    public int getUserID() 
    {
        return userID;
    }

    public String getUserName() 
    {
        return userName;
    }

    public String getEmailID() 
    {
        return emailID;
    }

    public String getPassword() 
    {
        return password;
    }

    public boolean getIsAdmin() 
    {
        return isAdmin;
    }

    public Gender getGender() 
    {
        return gender;
    }

    public Date getDob()
    {
        return dob;
    }

    
}

enum Gender
{
    Male,
    Female
}