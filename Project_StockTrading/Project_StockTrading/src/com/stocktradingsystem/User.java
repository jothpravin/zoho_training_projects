package com.stocktradingsystem;

import java.util.Date;

public class User 
{
    private int userid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long mobile_no;
    private Gender gender;
    private Date dob;

    public User(String firstName, String lastName, String email, String password, long mobile_no, Gender gender, Date dob) 
    {
        this.userid = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile_no = mobile_no;
        this.gender = gender;
        this.dob = dob;
    }

    public void setUserid(int userid) 
    {
        this.userid = userid;
    }

    public int getUserId() 
    {
        return userid;
    }
    public String getFirstName() 
    {
        return firstName;
    }
    public String getLastName() 
    {
        return lastName;
    }
    public String getEmail() 
    {
        return email;
    }
    public String getPassword() 
    {
        return password;
    }
    public long getMobile_no() 
    {
        return mobile_no;
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