package onlineBanking.model;

import java.util.Date;

public class Customer 
{
    private int customerId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date dob;
    private String email;
    private String password;
    private long mobileNo;
    private String streetAddress;
    private String city;
    private String state;
    private int pincode;
    private int cif_id;

    public Customer(String firstName, String lastName, Gender gender, Date dob, String email, String password, long mobileNo, String streetAddress, String city, String state, int pincode) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.mobileNo = mobileNo;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public void setCustomerId(int customerId) 
    {
        this.customerId = customerId;
    }

    public int getCIFId()
    {
        return cif_id;
    }

    public long getMobileNo()
    {
        return mobileNo;
    }

    public int getCustomerId() 
    {
        return customerId;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public Gender getGender() 
    {
        return gender;
    }

    public Date getDob() 
    {
        return dob;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPassword() 
    {
        return password;
    }

    public String getStreetAddress() 
    {
        return streetAddress;
    }

    public String getCity() 
    {
        return city;
    }

    public String getState() 
    {
        return state;
    }

    public int getPincode() 
    {
        return pincode;
    }

    
}
