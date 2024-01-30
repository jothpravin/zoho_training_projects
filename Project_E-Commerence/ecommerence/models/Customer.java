package ecommerence.models;

public class Customer
{
    private int customerId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String password;
    private long mobile_no;
    private long wallet;

    public Customer(String firstName, String lastName, Gender gender, String email, String password, long mobile_no, long wallet) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.mobile_no = mobile_no;
        this.wallet = wallet;
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

    public long getWallet() 
    {
        return wallet;
    }

    public void setCustomerId(int customerId) 
    {
        this.customerId = customerId;
    }

    public void setWallet(long wallet) 
    {
        this.wallet = wallet;
    }
}
