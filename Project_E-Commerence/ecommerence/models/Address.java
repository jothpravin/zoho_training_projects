package ecommerence.models;

public class Address 
{
    private int addressId;
    private String address;
    private String city;
    private String state;
    private int pinCode;
    private int customerId;

    public Address(String address, String city, String state, int pinCode, int customerId) 
    {
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.customerId = customerId;
    }

    public void setAddressId(int addressId) 
    {
        this.addressId = addressId;
    }

    public int getAddressId() 
    {
        return addressId;
    }

    public String getAddress() 
    {
        return address;
    }

    public int getCustomerId() 
    {
        return customerId;
    }

    public String getCity() 
    {
        return city;
    }

    public String getState() 
    {
        return state;
    }

    public int getPinCode() 
    {
        return pinCode;
    }    
}
