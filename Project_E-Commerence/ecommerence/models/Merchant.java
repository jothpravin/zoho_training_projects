package ecommerence.models;

public class Merchant 
{
    private int merchantId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long mobile_no;
    private String companyName;
    private String address;
    private String GST_no;
    private String PAN_no;
    private long amount;

    public Merchant(String firstName, String lastName, String email, String password, long mobile_no, String companyName, String address, String gST_no, String pAN_no) 
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mobile_no = mobile_no;
        this.companyName = companyName;
        this.address = address;
        GST_no = gST_no;
        PAN_no = pAN_no;
        this.amount = 0;
    }

    public void setMerchantId(int merchantId) 
    {
        this.merchantId = merchantId;
    }

    public void setAmount(long amount) 
    {
        this.amount = amount;
    }

    public int getMerchantId() 
    {
        return merchantId;
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

    public String getCompanyName() 
    {
        return companyName;
    }

    public String getAddress()
    {
        return address;
    }

    public String getGST_no() 
    {
        return GST_no;
    }

    public String getPAN_no() 
    {
        return PAN_no;
    }

    public long getAmount() 
    {
        return amount;
    }
}
