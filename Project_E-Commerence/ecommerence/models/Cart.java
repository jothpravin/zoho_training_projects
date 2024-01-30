package ecommerence.models;

import java.sql.Date;

public class Cart 
{
    private int cartId;
    private int customerId;
    private int productId;
    private int quantity;
    private double amount;
    private Date cartDate;

    public Cart(int customerId, int productId, int quantity, double amount) 
    {
        this.cartId = 0;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }

    public void setCartId(int cartId) 
    {
        this.cartId = cartId;
    }

    public void setCartDate(Date cartDate)
    {
        this.cartDate = cartDate;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getCartId() 
    {
        return cartId;
    }

    public int getCustomerId() 
    {
        return customerId;
    }

    public int getProductId() 
    {
        return productId;
    }

    public int getQuantity() 
    {
        return quantity;
    }

    public double getAmount() 
    {
        return amount;
    }

    public Date getCartDate() 
    {
        return cartDate;
    }
    
}
